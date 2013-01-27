package com.saucebot.twitch;

import com.saucebot.client.Channel;

public class Twitch {

    public static final int CHAT_PORT = 6667;

    public static String getAddressForChannel(final Channel channel) {
        return channel.getIdentifier() + ".jtvirc.com";
    }

    public static boolean isTwitchPM(final String username) {
        return "jtv".equals(username);
    }

    public static class Messages {
        public static final String PM_MODERATOR_LIST = "The moderators of this room are:";
    }

}