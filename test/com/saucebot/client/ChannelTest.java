package com.saucebot.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.saucebot.client.bot.BotAccount;
import com.saucebot.client.mock.MockBotAccountManager;

public class ChannelTest {

    @Test
    public void testChannel() {
        Channel enabledChannel = new Channel("Ravn", getBotAccount("Bot1"));
        Channel disabledChannel = new Channel("Test", getBotAccount("Bot2"));

        assertEquals("Ravn", enabledChannel.getName());
        assertEquals("Test", disabledChannel.getName());

        assertEquals("Bot1", enabledChannel.getBotName());
        assertEquals("Bot2", disabledChannel.getBotName());

    }

    private BotAccount getBotAccount(String username) {
        MockBotAccountManager manager = new MockBotAccountManager();
        return manager.createBotAccount(username, "password");
    }

}
