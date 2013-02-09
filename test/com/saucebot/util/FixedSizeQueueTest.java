package com.saucebot.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.saucebot.twitch.message.BotMessage;
import com.saucebot.twitch.message.Priority;

public class FixedSizeQueueTest {

    @Test
    public void testBasic() {
        FixedSizePriorityQueue<BotMessage> queue = getBasicQueue(5);

        assertFalse(queue.hasNext());
        assertNull(queue.next());
        assertEquals(0, queue.size());
    }

    @Test
    public void testBasicQueue() {
        FixedSizePriorityQueue<BotMessage> queue = getBasicQueue(5);

        BotMessage msg = new BotMessage("Hello");

        queue.add(msg);
        assertTrue(queue.hasNext());
        assertEquals(msg, queue.next());

        assertFalse(queue.hasNext());
        assertNull(queue.next());
    }

    @Test
    public void testFilledQueue() {
        int cap = 5;
        FixedSizePriorityQueue<BotMessage> queue = getBasicQueue(cap);

        for (int i = 1; i < cap; i++) {
            queue.add(new BotMessage("low" + i, Priority.LOW));
        }
        queue.add(new BotMessage("high1", Priority.HIGH));
        queue.add(new BotMessage("high2", Priority.HIGH));

        assertEquals("high1", queue.next().getMessage());
        assertEquals("high2", queue.next().getMessage());

        assertEquals("low1", queue.next().getMessage());
        assertEquals("low2", queue.next().getMessage());
        assertEquals("low3", queue.next().getMessage());
        assertFalse(queue.hasNext());
    }

    private FixedSizePriorityQueue<BotMessage> getBasicQueue(final int capacity) {
        return new FixedSizePriorityQueue<BotMessage>(capacity);
    }
}
