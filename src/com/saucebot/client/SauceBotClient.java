package com.saucebot.client;

import java.nio.file.Path;

import com.saucebot.client.bot.BotAccount;
import com.saucebot.client.bot.BotAccountManager;
import com.saucebot.client.bot.ConfigBotAccountManager;
import com.saucebot.config.JSONConfigParser;

public class SauceBotClient implements ChannelListListener {

    private final BotAccountManager accountManager;
    private final ChannelManager channelManager;

    public SauceBotClient(final Path configPath) {
        accountManager = new ConfigBotAccountManager(new JSONConfigParser(), configPath);
        channelManager = new ChannelManager();

        channelManager.setChannelListListener(this);
    }

    public void join(final String channelName, final String bot) {
        BotAccount account = accountManager.getBotAccount(bot);
        Channel channel = new Channel(channelName, account);
    }

	@Override
	public void channelAdded(Channel channel) {
		
	}

	@Override
	public void channelRemoved(Channel channel) {
		
	}

}
