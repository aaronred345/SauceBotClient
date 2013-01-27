package com.saucebot.twitch;

import java.util.HashSet;
import java.util.Set;

import com.saucebot.net.Connection;
import com.saucebot.net.ConnectionListener;

public class TMIClient implements ConnectionListener {

    private final String channelName;

    private Set<User> ops;

    private Connection connection;

    private final String accountName;
    private final String accountPassword;

    public TMIClient(final String channelName, final String accountName, final String accountPassword) {
        this.channelName = channelName;
        this.accountName = accountName;
        this.accountPassword = accountPassword;

        ops = new HashSet<User>();

        connection = new Connection(Twitch.getAddressForChannel(channelName), Twitch.CHAT_PORT);
    }

    public String getChannelName() {
        return channelName;
    }

    public Set<User> getOps() {
        return ops;
    }

    public boolean isOp(final User user) {
        return ops.contains(user);
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onMessageReceived(final String line) {

    }

}
