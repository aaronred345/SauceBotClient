package com.saucebot.test;

import com.saucebot.twitch.Message;
import com.saucebot.util.IRCUtils;

public class BenchmarkTest {

    public static void main(final String[] args) {
        checkParse();
        checkFormat();
    }

    private static void checkFormat() {
        int n = 100_000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < n / 2; i++) {
            IRCUtils.format("MODE", "#ravn", "+b", "testperson", "you are not nice!");
            IRCUtils.format("MODE", "#ravn", "+b", "testperson", "you are not nice!!");
        }
        long end = System.currentTimeMillis();

        System.out.println((end - start) / (n * 1.0) + "ms/format");
    }

    private static void checkParse() {
        int n = 100_000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < n / 2; i++) {
            Message.parse(":ravn_tm.tmi.twitch.tv 352 ravn_tm #ravn ravn ravn_tm.tmi.twitch.tv tmi.twitch.tv ravn H :0 ravn");
            Message.parse(":rravn_tm.tmi.twitch.tv 352 ravn_tm #ravn ravn ravn_tm.tmi.twitch.tv tmi.twitch.tv ravn H :0 ravn");
        }
        long end = System.currentTimeMillis();

        System.out.println((end - start) / (n * 1.0) + "ms/parse");
    }

}
