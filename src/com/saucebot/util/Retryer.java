package com.saucebot.util;

public class Retryer {

    private final long initialDelay;
    private final long maxDelay;
    private final double rate;

    private long delay;
    private int retryCount;

    public Retryer(final long initialDelay, final long maxDelay, final double rate) {
        this.initialDelay = initialDelay;
        this.maxDelay = maxDelay;
        this.rate = rate;
        reset();
    }

    public long getNextDelay() {
        long current = delay;
        retryCount++;
        delay *= rate;
        if (delay > maxDelay) {
            delay = maxDelay;
        }
        return current;
    }

    public int reset() {
        int count = retryCount;
        delay = initialDelay;
        retryCount = 0;

        return count;
    }

    public int getRetryCount() {
        return retryCount;
    }

}
