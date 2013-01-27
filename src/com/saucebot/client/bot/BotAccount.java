package com.saucebot.client.bot;

public class BotAccount {

    private final String username;
    private final String password;

    public BotAccount(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Bot[" + username + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BotAccount)) {
            return false;
        }
        BotAccount other = (BotAccount) obj;
        return other.getUsername().equals(getUsername()) && other.getPassword().equals(getPassword());
    }

    @Override
    public int hashCode() {
        return (username.hashCode() + 31) * 7 + password.hashCode();
    }

}
