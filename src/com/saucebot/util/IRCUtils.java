package com.saucebot.util;

public class IRCUtils {

    public static String format(final String code, final Object... args) {
        if (args.length == 0) {
            return code + '\r';
        }

        StringBuilder builder = new StringBuilder(code);

        int num = args.length;
        for (int i = 0; i < num - 1; i++) {
            builder.append(' ').append(args[i]);
        }

        String last = args[num - 1].toString().replaceAll("\\s+", " ");

        if (last.length() == 0 || last.indexOf(' ') == -1 || last.charAt(0) == ':') {
            return builder.append(' ').append(last).append('\r').toString();
        } else {
            return builder.append(" :").append(last).append('\r').toString();
        }
    }

    public static String[] parse(final String line) {
        String message = line.trim();
        if (message.isEmpty()) {
            return new String[0];
        }
        int colon = message.indexOf(':', 1);
        if (colon == -1) {
            return message.split(" ");
        } else {
            String pre = message.substring(0, colon);
            String post = message.substring(colon + 1);

            String[] preList = pre.split(" ");
            String[] fullList = new String[preList.length + 1];
            for (int i = 0; i < preList.length; i++) {
                fullList[i] = preList[i];
            }
            fullList[preList.length] = post;
            return fullList;
        }
    }
}
