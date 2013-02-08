package com.saucebot.client;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.saucebot.client.bot.BotAccount;
import com.saucebot.client.bot.BotAccountManager;
import com.saucebot.client.bot.ConfigBotAccountManager;
import com.saucebot.config.JSONConfigParser;
import com.saucebot.twitch.TwitchBot;

public class SauceBotClient implements ChannelListListener {

    private final BotAccountManager accountManager;
    private final ChannelManager channelManager;

    private Map<Channel, TwitchBot> bots;

    public SauceBotClient(final Path configPath) {
        accountManager = new ConfigBotAccountManager(new JSONConfigParser(), configPath);
        channelManager = new ChannelManager();

        bots = new HashMap<Channel, TwitchBot>();

        channelManager.setChannelListListener(this);
    }

    public void join(final String channelName, final String bot) {
        BotAccount account = accountManager.getBotAccount(bot);
        Channel channel = new Channel(channelName, account);

        channelManager.add(channel);
    }

    public void part(final String channelName, final String bot) {
        channelManager.remove(channelName);
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
