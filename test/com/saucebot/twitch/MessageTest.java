package com.saucebot.twitch;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.saucebot.twitch.message.IrcCode;
import com.saucebot.twitch.message.IrcMessage;

public class MessageTest {

    @Test
    public void testUnknown() {
        IrcMessage message = IrcMessage.parse("");
        assertEquals(0, message.getNumArgs());
        assertEquals(IrcCode.Unknown, message.getType());
    }

    @Test
    public void testWhoResponse() {
        String line = ":ravn_tm.tmi.twitch.tv 352 ravn_tm #ravn ravn ravn_tm.tmi.twitch.tv tmi.twitch.tv ravn H :0 ravn";
        IrcMessage message = IrcMessage.parse(line);

        assertEquals(8, message.getNumArgs());
        assertEquals("ravn_tm.tmi.twitch.tv", message.getSender());
        assertEquals("352", message.getCommand());
        assertEquals(IrcCode.Whoreply, message.getType());
        assertEquals(line, message.getRawLine());
        assertArrayEquals(new String[] { "ravn_tm", "#ravn", "ravn", "ravn_tm.tmi.twitch.tv", "tmi.twitch.tv", "ravn",
                "H", "0 ravn" }, message.getArgs());
    }

    @Test
    public void testChannelMessage() {
        String line = ":ravn_tm!ravn_tm@ravn_tm.tmi.twitch.tv PRIVMSG #ravn :hello world!";
        IrcMessage message = IrcMessage.parse(line);

        assertEquals(2, message.getNumArgs());
        assertEquals("#ravn", message.getArg(0));
        assertEquals("hello world!", message.getArg(1));
        assertEquals(IrcCode.Privmsg, message.getType());
        assertEquals("ravn_tm!ravn_tm@ravn_tm.tmi.twitch.tv", message.getSender());
        assertEquals("ravn_tm", message.getUser());
    }

    @Test
    public void testPM() {
        String line = ":jtv PRIVMSG saucebot :USERCOLOR saucebot green";
        IrcMessage message = IrcMessage.parse(line);

        assertEquals("jtv", message.getSender());
        assertEquals("PRIVMSG", message.getCommand());
        assertEquals(IrcCode.Privmsg, message.getType());
        assertEquals(2, message.getNumArgs());
        assertArrayEquals(new String[] { "saucebot", "USERCOLOR saucebot green" }, message.getArgs());
    }

    @Test
    public void testPing() {
        String line = "PING tmi.twitch.tv";
        IrcMessage message = IrcMessage.parse(line);

        assertEquals(null, message.getSender());
        assertEquals("PING", message.getCommand());
        assertEquals(IrcCode.Ping, message.getType());
        assertEquals(1, message.getNumArgs());
        assertArrayEquals(new String[] { "tmi.twitch.tv" }, message.getArgs());
    }
}
