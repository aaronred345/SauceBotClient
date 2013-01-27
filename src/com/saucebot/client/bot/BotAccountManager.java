package com.saucebot.client.bot;

import java.util.Set;

public interface BotAccountManager {

    public BotAccount getBotAccount(final String username);

    public boolean hasBotAccount(final String username);

    public Set<BotAccount> getBotAccounts();

}
