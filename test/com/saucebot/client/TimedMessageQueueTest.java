package com.saucebot.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.saucebot.twitch.TimedMessageQueue;
import com.saucebot.twitch.message.BotMessage;
import com.saucebot.twitch.message.Priority;

public class TimedMessageQueueTest {

    @Test
    public void testTimedMessageQueue() {
        TimedMessageQueue queue = new TimedMessageQueue(3, 10L);

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

    private void sleep(final long l) {
        try {
            Thread.sleep(l);
        } catch (Exception e) {
        }

    }

}
