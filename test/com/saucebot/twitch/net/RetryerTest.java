package com.saucebot.twitch.net;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.saucebot.util.Retryer;

public class RetryerTest {

    @Test
    public void testRetry() {
        long init = 500L;
        long max = 10000L;
        double rate = 1.5;

        Retryer retryer = new Retryer(init, max, rate);
        assertEquals(0, retryer.getRetryCount());
        long cur = init;
        assertEquals(init, retryer.getNextDelay());
        assertEquals(cur *= 1.5, retryer.getNextDelay());
        assertEquals(cur *= 1.5, retryer.getNextDelay());
        assertEquals(cur *= 1.5, retryer.getNextDelay());
        assertEquals(4, retryer.reset());
        cur = init;
        assertEquals(init, retryer.getNextDelay());
        assertEquals(cur *= 1.5, retryer.getNextDelay());
        assertEquals(2, retryer.getRetryCount());
        assertEquals(cur *= 1.5, retryer.getNextDelay());
        assertEquals(cur *= 1.5, retryer.getNextDelay());
        assertEquals(cur *= 1.5, retryer.getNextDelay());
        assertEquals(cur *= 1.5, retryer.getNextDelay());
        assertEquals(cur *= 1.5, retryer.getNextDelay());
        assertEquals(cur *= 1.5, retryer.getNextDelay());
        assertEquals(max, retryer.getNextDelay());
        assertEquals(max, retryer.getNextDelay());
        assertEquals(max, retryer.getNextDelay());
        assertEquals(11, retryer.getRetryCount());
        assertEquals(11, retryer.reset());
        assertEquals(init, retryer.getNextDelay());
    }

}