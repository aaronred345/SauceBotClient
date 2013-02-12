package com.saucebot.twitch;

import com.saucebot.client.Channel;
import com.saucebot.client.bot.BotAccount;
import com.saucebot.twitch.message.BotMessage;
import com.saucebot.twitch.message.Priority;
import com.saucebot.util.IteratorListener;
import com.saucebot.util.IteratorPoller;

public class TwitchBot implements IteratorListener<BotMessage> {

    private Channel channel;
    private BotAccount account;

    private TMIClient client;

    private TimedPriorityQueue<BotMessage> messageQueue;
    private IteratorPoller<BotMessage> botMessageListener;

    public TwitchBot(final Channel channel, final TMIListener listener) {
        this.channel = channel;
        this.account = channel.getBot();

        client = new TMIClient(channel.getIdentifier(), account.getUsername(), account.getPassword());
        client.setTMIListener(listener);

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

        System.out.printf("[%s] <*%s> %s\n", channel.getName(), account.getUsername(), message);

        client.sendMessage(message);
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
