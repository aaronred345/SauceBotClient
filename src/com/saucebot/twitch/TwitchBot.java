package com.saucebot.twitch;

import com.saucebot.client.Channel;
import com.saucebot.client.bot.BotAccount;
import com.saucebot.twitch.message.BotMessage;
import com.saucebot.twitch.message.Priority;
import com.saucebot.util.IteratorListener;
import com.saucebot.util.IteratorPoller;

public class TwitchBot implements TMIListener, IteratorListener<BotMessage> {

    private Channel channel;
    private BotAccount account;

    private TMIClient client;

    private TimedPriorityQueue<BotMessage> messageQueue;
    private IteratorPoller<BotMessage> botMessageListener;

    public TwitchBot(final Channel channel) {
        this.channel = channel;
        this.account = channel.getBot();

        client = new TMIClient(channel.getIdentifier(), account.getUsername(), account.getPassword());
        client.setTMIListener(this);

        messageQueue = new TimedPriorityQueue<BotMessage>(10, 3000L);
        botMessageListener = new IteratorPoller<>(messageQueue, this, 2000L);

    }

    public void close() {
        botMessageListener.stop();
        client.close();
    }

    public BotAccount getAccount() {
        return account;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public void onNext(final BotMessage element) {
        String message = element.getMessage();

        if (Priority.NORMAL.isBelowOrEqual(element.getPriority())) {
            message = NoiseGenerator.next() + " " + message;
        }

        client.sendMessage(message);
    }

    @Override
    public void onPrivateMessage(final String message) {
        System.out.printf("[PM/#%s] %s\n", channel.getName(), message);
    }

    @Override
    public void onMessage(final User user, final boolean isOp, final String text) {
        String name = user.getUsername();
        if (isOp) {
            name = "@" + name;
        }

        System.out.printf("[#%s] <%s> %s\n", channel.getName(), name, text);
    }

    @Override
    public void onJoin() {
        System.out.println("*** Joined " + channel.getName() + " ***");
    }

    @Override
    public void onPart() {
        System.out.println("*** Left " + channel.getName() + " ***");
    }

    public void sendMessage(final String message) {
        client.sendMessage(message);
    }

    public void send(final BotMessage message) {
        messageQueue.add(message);
    }

    @Override
    public String toString() {
        return "TwitchBot[" + getAccount().getUsername() + "@" + getChannel().getName() + "]";
    }

}
