package com.saucebot.util;

import java.util.Iterator;

public class IteratorPoller<T> implements Runnable {

    private static final long DEFAULT_TICK_DELAY = 1000L;

    private final Iterator<T> iterator;
    private final IteratorListener<T> listener;

    private final long tickDelay;

    private volatile boolean running;

    public IteratorPoller(final Iterator<T> iterator, final IteratorListener<T> listener, final long tickDelay) {
        this.iterator = iterator;
        this.listener = listener;
        this.tickDelay = tickDelay;

        start();
    }

    public IteratorPoller(final Iterator<T> iterator, final IteratorListener<T> listener) {
        this(iterator, listener, DEFAULT_TICK_DELAY);
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            pollForData();
            sleep();
        }
    }

    private void pollForData() {
        if (iterator.hasNext()) {
            T next = iterator.next();
            listener.onNext(next);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(tickDelay);
        } catch (InterruptedException ie) {

        }
    }

}
