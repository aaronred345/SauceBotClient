package com.saucebot.twitch;

import com.saucebot.twitch.message.BotMessage;
import com.saucebot.util.FixedSizePriorityQueue;

public class TimedMessageQueue {

    private final long delay;

    private FixedSizePriorityQueue<BotMessage> queue;

    private long lastMessageTime;
    private BotMessage lastMessage;

    public TimedMessageQueue(final int size, final long delay) {
        this.delay = delay;
        this.lastMessageTime = 0L;

        queue = new FixedSizePriorityQueue<>(size);
    }

    public void add(final BotMessage message) {
        if (!isMessageCached(message)) {
            queue.add(message);
        }
    }

    private boolean isMessageCached(final BotMessage message) {
        return message.equals(lastMessage) && lastMessageTime + delay * 10 > System.currentTimeMillis();
    }

    public boolean hasNext() {
        return queue.hasNext() && lastMessageTime + delay < System.currentTimeMillis();
    }

    public BotMessage next() {
        lastMessageTime = System.currentTimeMillis();
        lastMessage = queue.next();
        return lastMessage;
    }

    @Override
    public String toString() {
        return "MessageQueue[" + queue.size() + "]";
    }

}
