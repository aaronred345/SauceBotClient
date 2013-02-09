package com.saucebot.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.saucebot.twitch.TimedPriorityQueue;
import com.saucebot.twitch.message.BotMessage;
import com.saucebot.twitch.message.Priority;

public class TimedMessageQueueTest {

    @Test
    public void testTimedMessageQueue() {
        TimedPriorityQueue queue = new TimedPriorityQueue(3, 10L);

        BotMessage high = new BotMessage("high", Priority.HIGH);
        BotMessage high2 = new BotMessage("high2", Priority.HIGH);

        BotMessage med = new BotMessage("medium", Priority.NORMAL);
        BotMessage med2 = new BotMessage("medium2", Priority.NORMAL);

        BotMessage low = new BotMessage("low", Priority.LOW);
        BotMessage low2 = new BotMessage("low2", Priority.LOW);

        queue.add(low);
        queue.add(high);
        queue.add(med2);
        queue.add(low2);
        queue.add(med);
        queue.add(high2);

        assertTrue(queue.hasNext());
        assertEquals(high, queue.next());
        assertFalse(queue.hasNext());
        sleep(15L);
        assertTrue(queue.hasNext());
        assertEquals(high2, queue.next());
        assertFalse(queue.hasNext());
        sleep(5L);
        assertFalse(queue.hasNext());
        sleep(10L);
        assertTrue(queue.hasNext());
        assertEquals(med, queue.next());
        sleep(15L);
        assertFalse(queue.hasNext());

    }

    @Test
    public void testCached() {
        TimedPriorityQueue queue = new TimedPriorityQueue(3, 10L);

        BotMessage msg1 = new BotMessage("Hello");
        BotMessage msg2 = new BotMessage("Hiya");

        queue.add(msg1);
        queue.add(msg2);

        assertTrue(queue.hasNext());
        assertEquals(msg1, queue.next());
        sleep(15L);
        assertTrue(queue.hasNext());
        assertEquals(msg2, queue.next());
        sleep(15L);
        assertFalse(queue.hasNext());
        queue.add(msg2);
        assertFalse(queue.hasNext());
        queue.add(msg1);
        sleep(15L);
        assertTrue(queue.hasNext());
        assertEquals(msg1, queue.next());
        queue.add(msg1);
        sleep(15L);
        assertFalse(queue.hasNext());
        sleep(120L);
        queue.add(msg1);
        assertTrue(queue.hasNext());
        assertEquals(msg1, queue.next());

    }

    private void sleep(final long l) {
        try {
            Thread.sleep(l);
        } catch (Exception e) {
        }

    }

}
