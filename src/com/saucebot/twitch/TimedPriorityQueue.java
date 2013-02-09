package com.saucebot.twitch;

import java.util.Iterator;

import com.saucebot.util.FixedSizePriorityQueue;

public class TimedPriorityQueue<T extends Comparable<? super T>> implements Iterator<T> {

    private final long delay;

    private FixedSizePriorityQueue<T> queue;

    private long lastMessageTime;
    private T lastMessage;

    public TimedPriorityQueue(final int size, final long delay) {
        this.delay = delay;
        this.lastMessageTime = 0L;

        queue = new FixedSizePriorityQueue<T>(size);
    }

    public void add(final T message) {
        if (!isValueCached(message)) {
            queue.add(message);
        }
    }

    private boolean isValueCached(final T message) {
        return message.equals(lastMessage) && (lastMessageTime + delay * 10) > System.currentTimeMillis();
    }

    @Override
    public boolean hasNext() {
        return queue.hasNext() && lastMessageTime + delay < System.currentTimeMillis();
    }

    @Override
    public T next() {
        lastMessageTime = System.currentTimeMillis();
        lastMessage = queue.next();
        return lastMessage;
    }

    @Override
    public void remove() {

    }

    @Override
    public String toString() {
        return "TimedPriorityQueue[" + queue.size() + ", " + queue + "]";
    }

}
