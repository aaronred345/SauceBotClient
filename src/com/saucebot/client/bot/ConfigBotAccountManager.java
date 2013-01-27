package com.saucebot.client.bot;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.saucebot.config.ConfigObject;
import com.saucebot.config.ConfigParser;

public class ConfigBotAccountManager implements BotAccountManager {

    private Map<String, BotAccount> botAccounts;

    private BotAccount defaultAccount;

    private ConfigBotAccountManager() {
        botAccounts = new HashMap<String, BotAccount>();
    }

    public ConfigBotAccountManager(final ConfigParser parser, final Path path) {
        this();
        loadAccountsFromParser(parser, path);
    }

    public ConfigBotAccountManager(final ConfigObject configData) {
        this();
        loadAccountsFromConfigObject(configData);
    }

    private void loadAccountsFromParser(final ConfigParser parser, final Path path) {
        ConfigObject root = parser.parse(path);
        ConfigObject accounts = root.get("accounts");
        loadAccountsFromConfigObject(accounts);
    }

    private void loadAccountsFromConfigObject(final ConfigObject accounts) {
        for (Map.Entry<String, ConfigObject> account : accounts.getMap().entrySet()) {
            loadAccountFromConfigMapEntry(account);
        }
    }

    private void loadAccountFromConfigMapEntry(final Map.Entry<String, ConfigObject> account) {
        String username = account.getKey();
        String password = account.getValue().getString();

        addBotAccount(username, password);
    }

    private void addBotAccount(final String username, final String password) {
        BotAccount botAccount = new BotAccount(username, password);
        botAccounts.put(username.toLowerCase(), botAccount);
    }

    public void setDefaultAccount(final BotAccount account) {
        this.defaultAccount = account;
    }

    public BotAccount getDefaultAccount() {
        return defaultAccount;
    }

    public boolean hasDefaultAccount() {
        return defaultAccount != null;
    }

    @Override
    public BotAccount getBotAccount(final String username) {
        if (username == null) {
            return defaultAccount;
        }
        return botAccounts.get(username.toLowerCase());
    }

    @Override
    public boolean hasBotAccount(final String username) {
        if (username == null) {
            return hasDefaultAccount();
        }
        return botAccounts.containsKey(username.toLowerCase());
    }

    @Override
    public Set<BotAccount> getBotAccounts() {
        return new HashSet<BotAccount>(botAccounts.values());
    }

}
