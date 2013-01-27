package com.saucebot.net;

import java.net.Socket;

public class Connection implements SocketListener {

    private final String host;
    private final int port;

    private AutoSocket socket;

    private ConnectionListener listener;

    public Connection(final String host, final int port) {
        this.host = host;
        this.port = port;
        setConnectionListener(null);
    }

    public void setConnectionListener(final ConnectionListener listener) {
        if (listener == null) {
            this.listener = new NullListener();
        } else {
            this.listener = listener;
        }
    }

    public void connect() {
        socket = new AutoSocket(host, port, this);
    }

    @Override
    public void onMessageReceived(final String line) {
        listener.onMessageReceived(line);
    }

    @Override
    public void onSocketClosed() {
        listener.onDisconnected();
    }

    @Override
    public void onSocketConnected(final Socket socket) {
        listener.onConnected();
    }

    public void send(final String message) {
        socket.write(message);
    }

    static final class NullListener implements ConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected() {
        }

        @Override
        public void onMessageReceived(final String line) {
        }

    }

}
