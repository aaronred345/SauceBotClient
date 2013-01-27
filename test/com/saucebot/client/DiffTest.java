package com.saucebot.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.saucebot.client.bot.BotAccount;
import com.saucebot.util.Diff;

public class DiffTest {

    @Test
    public void testListDifference() {
        List<Integer> listA = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> listB = Arrays.asList(1, 2, 3, 6, 7);

        Diff<Integer> diff = Diff.calculate(listA, listB);
        assertTrue(diff.isDifferent());
        assertFalse(diff.isSame());

        Set<Integer> added = diff.getAdded();
        Set<Integer> removed = diff.getRemoved();

        assertEquals(2, added.size());
        assertTrue(added.contains(6));
        assertTrue(added.contains(7));

        assertEquals(2, removed.size());
        assertTrue(removed.contains(4));
        assertTrue(removed.contains(5));
    }

    @Test
    public void testChannelDifference() {
        BotAccount b1 = new BotAccount("Bot1", "asf");
        BotAccount b2 = new BotAccount("Bot2", "dfg");

        List<Channel> listA = Arrays.asList(new Channel("Ravn", b1), new Channel("Test", b1), new Channel("Asdf", b2));
        List<Channel> listB = Arrays.asList(new Channel("Ravn", b1), new Channel("Test", b2));

        Diff<Channel> diff = Diff.calculate(listA, listB);
        assertTrue(diff.isDifferent());

        Set<Channel> added = diff.getAdded();
        Set<Channel> removed = diff.getRemoved();

        assertEquals(1, added.size());
        assertEquals(2, removed.size());

        assertTrue(added.contains(new Channel("Test", b2)));
        assertTrue(removed.contains(new Channel("Asdf", b2)));
        assertTrue(removed.contains(new Channel("Test", b1)));
    }
}
