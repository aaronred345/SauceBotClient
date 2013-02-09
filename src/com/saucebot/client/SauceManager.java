package com.saucebot.client;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.saucebot.client.bot.BotAccount;
import com.saucebot.client.bot.BotAccountManager;
import com.saucebot.client.bot.ConfigBotAccountManager;
import com.saucebot.client.emit.Emit;
import com.saucebot.client.emit.EmitHandler;
import com.saucebot.client.emit.EmitType;
import com.saucebot.config.ConfigObject;
import com.saucebot.config.JSONConfigParser;
import com.saucebot.twitch.TwitchBot;
import com.saucebot.twitch.message.BotMessage;

public class SauceManager implements ChannelListListener, SauceListener {

    private final BotAccountManager accountManager;
    private final ChannelManager channelManager;

    private Map<Channel, TwitchBot> bots;

    private SauceConnection saucebot;

    public SauceManager(final Path configPath) {
        JSONConfigParser parser = new JSONConfigParser();
        ConfigObject configurations = parser.parse(configPath);

        accountManager = new ConfigBotAccountManager(configurations);
        channelManager = new ChannelManager();

        bots = new HashMap<Channel, TwitchBot>();

        channelManager.setChannelListListener(this);

        setupServerConnection(configurations.get("server"));

    }

    private void setupServerConnection(final ConfigObject serverConfig) {
        String host = serverConfig.get("host").getString();
        int port = serverConfig.get("port").getInt();

        saucebot = new SauceConnection(host, port);
        saucebot.registerHandlers(this);
    }

    @EmitHandler(EmitType.CHANNELS)
    public void onChannels(final Emit emit) {
        System.out.println("CHANNELS: " + emit);
    }

    @EmitHandler(EmitType.SAY)
    public void onSay(final Emit emit) {
        String chan = emit.get("chan");
        String msg = emit.get("msg");
        BotMessage message = new BotMessage(msg);
        sendToChannel(chan, message);
    }

    @EmitHandler(EmitType.UNBAN)
    public void onUnban(final Emit emit) {
        System.out.println("UNBAN: " + emit);
    }

    @EmitHandler(EmitType.BAN)
    public void onBan(final Emit emit) {
        System.out.println("BAN: " + emit);
    }

    @EmitHandler(EmitType.TIMEOUT)
    public void onTimeout(final Emit emit) {
        System.out.println("TIMEOUT: " + emit);
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

    @Override
    public void channelAdded(final Channel channel) {
        TwitchBot bot = bots.get(channel);
        if (bot != null) {
            channelRemoved(channel);
        }

        bot = new TwitchBot(channel);
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
