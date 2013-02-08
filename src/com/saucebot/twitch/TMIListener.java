package com.saucebot.twitch;


public interface TMIListener {

    public void onPrivateMessage(String message);

    public void onMessage(User user, boolean isOp, String text);

    public void onJoin();

    public void onPart();

}
