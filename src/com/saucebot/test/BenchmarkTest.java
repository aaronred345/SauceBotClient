package com.saucebot.test;

import com.saucebot.client.emit.Emit;
import com.saucebot.client.emit.EmitType;
import com.saucebot.client.emit.EmitUtils;
import com.saucebot.twitch.message.IrcMessage;
import com.saucebot.util.IRCUtils;

public class BenchmarkTest {

    public static void main(final String[] args) {
        checkIrcParse();
        checkIrcFormat();

        checkJsonParse();
        checkJsonFormat();
    }

    private static void checkJsonFormat() {
        int n = 100_000;
        long start = System.currentTimeMillis();
        Emit emit = new Emit(EmitType.TIMEOUT);
        emit.put("a", "123");
        emit.put("b", "456");
        emit.put("c", "12312");

        for (int i = 0; i < n; i++) {
            EmitUtils.serialize(emit);
        }
        long end = System.currentTimeMillis();

        System.out.println((end - start) / (n * 1.0) + "ms/jsonformat");
    }

    private static void checkJsonParse() {
        int n = 100_000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            EmitUtils.parse("{\"cmd\":\"msg\",\"data\":{\"a\":1,\"b\":2,\"c\":\"asdf\"}}");
        }
        long end = System.currentTimeMillis();

        System.out.println((end - start) / (n * 1.0) + "ms/jsonparse");
    }

    private static void checkIrcFormat() {
        int n = 100_000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < n / 2; i++) {
            IRCUtils.format("MODE", "#ravn", "+b", "testperson", "you are not nice!");
            IRCUtils.format("MODE", "#ravn", "+b", "testperson", "you are not nice!!");
        }
        long end = System.currentTimeMillis();

        System.out.println((end - start) / (n * 1.0) + "ms/format");
    }

    private static void checkIrcParse() {
        int n = 100_000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < n / 2; i++) {
            IrcMessage
                    .parse(":ravn_tm.tmi.twitch.tv 352 ravn_tm #ravn ravn ravn_tm.tmi.twitch.tv tmi.twitch.tv ravn H :0 ravn");
            IrcMessage
                    .parse(":rravn_tm.tmi.twitch.tv 352 ravn_tm #ravn ravn ravn_tm.tmi.twitch.tv tmi.twitch.tv ravn H :0 ravn");
        }
        long end = System.currentTimeMillis();

        System.out.println((end - start) / (n * 1.0) + "ms/parse");
    }

}
