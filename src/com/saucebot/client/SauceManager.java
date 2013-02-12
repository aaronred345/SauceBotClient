package com.saucebot.client;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.saucebot.client.bot.BotAccount;
import com.saucebot.client.bot.BotAccountManager;
import com.saucebot.client.bot.ConfigBotAccountManager;
import com.saucebot.client.emit.Emit;
import com.saucebot.client.emit.EmitHandler;
import com.saucebot.client.emit.EmitParser;
import com.saucebot.client.emit.EmitType;
import com.saucebot.config.ConfigObject;
import com.saucebot.config.JSONConfigParser;
import com.saucebot.twitch.TMIClient;
import com.saucebot.twitch.TMIListener;
import com.saucebot.twitch.Twitch;
import com.saucebot.twitch.TwitchBot;
import com.saucebot.twitch.User;
import com.saucebot.twitch.message.BotMessage;

public class SauceManager implements ChannelListListener, SauceListener, TMIListener {

    private final BotAccountManager accountManager;
    private final ChannelManager channelManager;

    private final EmitParser emitParser;

    private Map<Channel, TwitchBot> bots;

    private SauceConnection saucebot;

    public SauceManager(final Path configPath) {
        JSONConfigParser parser = new JSONConfigParser();
        ConfigObject configurations = parser.parse(configPath);

        accountManager = new ConfigBotAccountManager(configurations.get("accounts"));
        channelManager = new ChannelManager();
        emitParser = new EmitParser(accountManager, channelManager);

        bots = new HashMap<Channel, TwitchBot>();

        channelManager.setChannelListListener(this);

        setupServerConnection(configurations.get("server"));

    }

    private void setupServerConnection(final ConfigObject serverConfig) {
        String host = serverConfig.get("host").getString();
        int port = serverConfig.get("port").getInt();

        saucebot = new SauceConnection(host, port);
        saucebot.registerHandlers(this);
        saucebot.connect();
    }

    @EmitHandler(EmitType.CHANNELS)
    public void onChannels(final Emit emit) {
        emitParser.parseChannelEmit(emit);

    }

    @EmitHandler(EmitType.SAY)
    public void onSay(final Emit emit) {
        BotMessage message = emitParser.parseSayEmit(emit);
        sendMessageToEmitChannel(message, emit);
    }

    @EmitHandler(EmitType.UNBAN)
    public void onUnban(final Emit emit) {
        BotMessage message = emitParser.parseUnbanEmit(emit);
        sendMessageToEmitChannel(message, emit);
    }

    @EmitHandler(EmitType.BAN)
    public void onBan(final Emit emit) {
        BotMessage message = emitParser.parseBanEmit(emit);
        sendMessageToEmitChannel(message, emit);
    }

    @EmitHandler(EmitType.TIMEOUT)
    public void onTimeout(final Emit emit) {
        BotMessage message = emitParser.parseTimeoutEmit(emit);
        sendMessageToEmitChannel(message, emit);
    }

    @EmitHandler(EmitType.ERROR)
    public void onError(final Emit emit) {
        String message = emit.get("msg");
        System.err.println("[Error] " + message);
    }

    @Override
    public void onEmit(final Emit emit) {
        System.out.println("UNKNOWN: " + emit);
    }

    @Override
    public void onConnect() {
        Emit reg = new Emit(EmitType.REGISTER);
        reg.put("type", "chat");
        reg.put("name", "JavaClient");
        saucebot.write(reg);
    }

    @Override
    public void onDisconnect() {
        System.out.println("Closed!");
    }

    public void join(final String channelName, final String bot) {
        BotAccount account = accountManager.getBotAccount(bot);
        Channel channel = new Channel(channelName, account);

        channelManager.add(channel);
    }

    public void part(final String channelName, final String bot) {
        channelManager.remove(channelName);
    }

    private void sendMessageToEmitChannel(final BotMessage message, final Emit emit) {
        String channelName = emit.get("chan");
        sendToChannel(channelName, message);
    }

    private void sendToChannel(final String channelName, final BotMessage message) {
        Channel channel = channelManager.get(channelName);
        TwitchBot bot = bots.get(channel);
        bot.send(message);
    }

    public void say(final Channel channel, final String message) {
        TwitchBot bot = bots.get(channel);
        if (bot == null) {
            System.err.println("No such channel: " + message);
        } else {
            bot.sendMessage(message);
        }
    }

    /* TMI Listener */

    @Override
    public void onPrivateMessage(final TMIClient source, final String message) {
        System.out.printf("[%s] %s\n", source.getChannelName(), message);

        Emit emit = new Emit(EmitType.PRIVATE_MESSAGE);
        emit.put("user", Twitch.SYSTEM_MESSAGE_USER);
        emit.put("msg", message);
        saucebot.write(emit);
    }

    @Override
    public void onMessage(final TMIClient source, final User user, final boolean isOp, final String text) {
        System.out.printf("[%s] <%c%s> %s\n", source.getChannelName(), isOp ? '@' : ' ', user.getUsername(), text);

        Emit emit = new Emit(EmitType.MESSAGE);
        emit.put("op", isOp ? "1" : "0");
        emit.put("user", user.getUsername());
        emit.put("msg", text);
        emit.put("chan", source.getChannelName());
        saucebot.write(emit);
    }

    @Override
    public void onJoin(final TMIClient source) {
        System.out.println("*** JOINED " + source.getChannelName());
    }

    @Override
    public void onPart(final TMIClient source) {
        System.out.println("*** LEFT " + source.getChannelName());
    }

    /* ChannelListener */

    @Override
    public void channelAdded(final Channel channel) {
        TwitchBot bot = bots.get(channel);
        if (bot != null) {
            channelRemoved(channel);
        }

        bot = new TwitchBot(channel, this);
        bots.put(channel, bot);
    }

    @Override
    public void channelRemoved(final Channel channel) {
        TwitchBot bot = bots.get(channel);
        if (bot == null) {
            return;
        }

        bot.close();
        bots.remove(channel);
    }

}
