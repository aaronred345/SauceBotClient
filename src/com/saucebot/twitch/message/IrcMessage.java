package com.saucebot.twitch.message;

import com.saucebot.util.ArrayUtils;
import com.saucebot.util.IRCUtils;

public class IrcMessage implements Message {

    private static final IrcMessage NullMessage = new IrcMessage(null, "", IrcCode.Unknown, "", new String[0]);

    private final String sender;
    private final String user;
    private final String command;
    private final IrcCode type;
    private final String raw;
    private final String[] arguments;

    private IrcMessage(final String sender, final String command, final IrcCode type, final String raw,
            final String[] arguments) {
        this.sender = sender;
        this.command = command;
        this.type = type;
        this.raw = raw;
        this.arguments = arguments;

        if (sender != null) {
            int idx = sender.indexOf('!');
            if (idx > -1) {
                this.user = sender.substring(0, idx);
            } else {
                this.user = sender;
            }
        } else {
            this.user = null;
        }
    }

    public String getSender() {
        return sender;
    }

    public String getUser() {
        return user;
    }

    @Override
    public IrcCode getType() {
        return type;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public int getNumArgs() {
        return arguments.length;
    }

    @Override
    public String getArg(final int index) {
        return arguments[index];
    }

    @Override
    public String[] getArgs() {
        return arguments;
    }

    @Override
    public String getRawLine() {
        return raw;
    }

    public static IrcMessage parse(final String line) {
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

        String[] params = ArrayUtils.slice(parts, paramStart);

        code = IrcCode.getForCode(command);
        return new IrcMessage(sender, command, code, line, params);
    }

    @Override
    public String toString() {
        return "Message[" + raw + "]";
    }

}
