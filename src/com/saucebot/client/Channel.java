package com.saucebot.client;

import com.saucebot.client.bot.BotAccount;

public class Channel {

    private final String name;
    private final String identifier;

    private final BotAccount bot;

    public Channel(final String name, final BotAccount bot) {
        this.name = name;
        this.identifier = name.toLowerCase();
        this.bot = bot;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public BotAccount getBot() {
        return bot;
    }

    @Override
    public String toString() {
        return "Channel[" + getName() + ":" + getBotName() + "]";
    }

    public String getBotName() {
        return bot.getUsername();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof Channel)) {
            return false;
        }
        Channel other = (Channel) obj;
        return identifier.equals(other.getIdentifier()) && bot.equals(other.getBot());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + getIdentifier().hashCode();
        hash = 31 * hash + getBot().hashCode();
        return hash;
    }

}
