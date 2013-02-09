package com.saucebot.client;

import com.saucebot.client.emit.Emit;

public interface SauceListener {

    public void onConnect();

    public void onDisconnect();

    public void onEmit(Emit emit);

}
