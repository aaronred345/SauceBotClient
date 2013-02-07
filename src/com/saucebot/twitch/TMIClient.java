package com.saucebot.twitch;

import java.util.HashSet;
import java.util.Set;

import com.saucebot.net.Connection;
import com.saucebot.net.ConnectionListener;
import com.saucebot.util.IRCUtils;

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
        connection.setConnectionListener(this);
        connection.connect();
    }

    public void close() {
        connection.close();
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
        send("PASS", this.accountPassword);
        send("NICK", this.accountName);
        send("USER", this.accountName, 8, '*', this.accountName);

        send("JOIN", '#' + this.channelName);
        send("JTVROOMS", '#' + this.channelName);
        send("JTVCLIENT", '#' + this.channelName);
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onMessageReceived(final String line) {
        Message message = Message.parse(line);
        switch (message.getType()) {
        case Privmsg:
            String channel = message.getArg(0);
            String user = message.getUser();
            String text = message.getArg(1);
            System.out.printf("%s <%s> %s\n", channel, user, text);
        default:
            System.out.println(message.getType().name() + ": " + message);
        }
    }

    private void send(final String code, final Object... args) {
        connection.send(IRCUtils.format(code, args));
    }

    public void sendMessage(final String line) {
        send("PRIVMSG", '#' + this.channelName, line);
    }

}
