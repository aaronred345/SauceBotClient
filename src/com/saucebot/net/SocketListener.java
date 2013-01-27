package com.saucebot.net;

import java.net.Socket;

public interface SocketListener extends MessageListener {

    public void onSocketClosed();

    public void onSocketConnected(final Socket socket);

}
