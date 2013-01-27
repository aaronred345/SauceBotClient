package com.saucebot.client.mock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.saucebot.client.bot.BotAccount;
import com.saucebot.client.bot.BotAccountManager;

public class MockBotAccountManager implements BotAccountManager {

    private Map<String, BotAccount> botAccounts;

    public MockBotAccountManager() {
        botAccounts = new HashMap<String, BotAccount>();
    }

    public BotAccount createBotAccount(final String username, final String password) {

        BotAccount account = new BotAccount(username, password);
        botAccounts.put(username.toLowerCase(), account);
        return account;
    }

    @Override
    public BotAccount getBotAccount(final String username) {
        return botAccounts.get(username.toLowerCase());
    }

    @Override
    public boolean hasBotAccount(String username) {
        return botAccounts.containsKey(username.toLowerCase());
    }

    @Override
    public Set<BotAccount> getBotAccounts() {
        return new HashSet<BotAccount>(botAccounts.values());
    }

}
