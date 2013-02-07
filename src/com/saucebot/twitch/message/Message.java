package com.saucebot.twitch.message;

public interface Message {

    public IrcCode getType();

    public String getRawLine();

    public int getNumArgs();

    public String getArg(int idx);

    public String[] getArgs();

}