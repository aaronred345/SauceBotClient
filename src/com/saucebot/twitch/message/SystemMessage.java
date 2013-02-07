package com.saucebot.twitch.message;

import com.saucebot.util.ArrayUtils;
import com.saucebot.util.IRCUtils;

public class SystemMessage implements Message {

    private static final SystemMessage NullMessage = new SystemMessage(IrcCode.Unknown, new String[0], "");

    private final IrcCode type;
    private final String[] args;

    private final String raw;

    private SystemMessage(final IrcCode type, final String[] args, final String rawLine) {
        this.type = type;
        this.args = args;
        this.raw = rawLine;
    }

    public static SystemMessage parse(final String line) {
        String[] parts = IRCUtils.parseBasic(line);
        if (parts.length == 0 || parts[0].isEmpty()) {
            return NullMessage;
        }

        IrcCode type = IrcCode.getForCode(parts[0]);
        if (parts.length == 1) {
            return new SystemMessage(type, new String[0], line);
        } else {
            String[] args = ArrayUtils.slice(parts, 1);
            return new SystemMessage(type, args, line);
        }
    }

    @Override
    public IrcCode getType() {
        return type;
    }

    @Override
    public String getRawLine() {
        return raw;
    }

    @Override
    public int getNumArgs() {
        return args.length;
    }

    @Override
    public String getArg(final int idx) {
        return args[idx];
    }

    @Override
    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "SystemMessage[" + raw + "]";
    }

}
