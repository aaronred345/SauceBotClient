package com.saucebot.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IRCUtilsTest {

    @Test
    public void testFormat() {
        assertEquals("\r", IRCUtils.format(""));
        assertEquals("TEST\r", IRCUtils.format("TEST"));

        assertEquals("Hello :world!\r", IRCUtils.format("Hello", "world!"));
        assertEquals("PRIVMSG #ravn :Hello there everyone. :-)\r",
                IRCUtils.format("PRIVMSG", "#ravn", "Hello there everyone. :-)"));

        assertEquals("PRIVMSG ::test\r", IRCUtils.format("PRIVMSG", ":test"));
        assertEquals("A :It's currently 02:30\r", IRCUtils.format("A", "It's currently 02:30"));

        assertEquals("A 2 3 * :D E F\r", IRCUtils.format("A", 2, 3, '*', "D\rE\rF"));
        assertEquals("PASS :hello this is a test\r", IRCUtils.format("PASS", "hello    \tthis\t\nis a \n\ftest"));
    }

    @Test
    public void testParse() {
        arrayEquals(IRCUtils.parse(""));
        arrayEquals(IRCUtils.parse("a"), "a");
        arrayEquals(IRCUtils.parse("a #b c"), "a", "#b", "c");
        arrayEquals(IRCUtils.parse("   a #b c asdfasd_ASDFAsdf    "), "a", "#b", "c", "asdfasd_ASDFAsdf");
        arrayEquals(IRCUtils.parse("PRIVMSG #ravn ravn@ravn Hello"), "PRIVMSG", "#ravn", "ravn@ravn", "Hello");
        arrayEquals(IRCUtils.parse("PRIVMSG #ravn :Hello   world!"), "PRIVMSG", "#ravn", "Hello   world!");
        arrayEquals(IRCUtils.parse("PRIVMSG #ravn :Hello world:!"), "PRIVMSG", "#ravn", "Hello world:!");
        arrayEquals(IRCUtils.parse("A B C :"), "A", "B", "C", "");
        arrayEquals(IRCUtils.parse("A B C ::"), "A", "B", "C", ":");
    }

    private void arrayEquals(final String[] actual, final String... expected) {
        assertArrayEquals(expected, actual);
    }
}
