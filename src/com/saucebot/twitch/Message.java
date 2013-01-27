package com.saucebot.twitch;

import com.saucebot.util.IRCUtils;

public class Message {

    private static final Message NullMessage = new Message(null, "", IrcCode.Unknown, "", new String[0]);

    private final String sender;
    private final String command;
    private final IrcCode type;
    private final String raw;
    private final String[] arguments;

    private Message(final String sender, final String command, final IrcCode type, final String raw,
            final String[] arguments) {
        this.sender = sender;
        this.command = command;
        this.type = type;
        this.raw = raw;
        this.arguments = arguments;
    }

    public String getSender() {
        return sender;
    }

    public IrcCode getType() {
        return type;
    }

    public String getCommand() {
        return command;
    }

    public int getNumArgs() {
        return arguments.length;
    }

    public String getArg(final int index) {
        return arguments[index];
    }

    public String[] getArgs() {
        return arguments;
    }

    public String getRawLine() {
        return raw;
    }

    public static Message parse(final String line) {
        String[] parts = IRCUtils.parse(line);
        if (parts.length == 0 || parts[0].isEmpty()) {
            return NullMessage;
        }

        int paramStart = 1;

        String sender = null;
        String command = null;
        IrcCode code = IrcCode.Unknown;

        if (parts[0].charAt(0) == ':') {
            sender = parts[0].substring(1);
            command = parts[1];
            paramStart = 2;
        } else {
            command = parts[0];
        }

        String[] params = slice(parts, paramStart);

        code = IrcCode.getForCode(command);
        return new Message(sender, command, code, line, params);
    }

    private static String[] slice(final String[] array, final int startIndex) {
        int length = array.length;
        String[] spliced = new String[length - startIndex];
        for (int i = startIndex; i < length; i++) {
            spliced[i - startIndex] = array[i];
        }
        return spliced;
    }

    @Override
    public String toString() {
        return "Message[" + raw + "]";
    }

}
