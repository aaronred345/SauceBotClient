package com.saucebot.twitch;

public interface TMIListener {

    public void onPrivateMessage(TMIClient source, String message);

    public void onMessage(TMIClient source, User user, boolean isOp, String text);

    public void onJoin(TMIClient source);

    public void onPart(TMIClient source);

}
