package com.saucebot.net;

public interface ConnectionListener {

    public void onConnected();

    public void onDisconnected();

    public void onMessageReceived(final String line);

}
