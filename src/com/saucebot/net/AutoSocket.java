package com.saucebot.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.saucebot.util.Retryer;

public class AutoSocket implements Runnable, MessageListener {

    private static final long CHECK_DELAY = 2500L;

    private Retryer connectionRetryer;

    private final String host;
    private final int port;

    private Socket socket;
    private PrintWriter writer;

    private volatile boolean running;

    private final SocketListener listener;

    private long inactivityTimeLimit;
    private volatile long lastMessageReceived;

    public AutoSocket(final String host, final int port, final SocketListener listener) {
        this.host = host;
        this.port = port;
        this.listener = listener;

        connectionRetryer = new Retryer(500L, 60 * 1000, 1.5);
    }

    public void setInactivityTimeLimit(final long inactivityTimeLimit) {
        this.inactivityTimeLimit = inactivityTimeLimit;
    }

    public long getInactivityTimeLimit() {
        return inactivityTimeLimit;
    }

    public void open() {
        running = true;
        lastMessageReceived = System.currentTimeMillis();
        new Thread(this).start();
    }

    public void close() {
        running = false;
    }

    public void requestReconnect() {
        closeConnection();
    }

    @Override
    public void run() {
        while (running) {
            checkConnection();
        }
        closeConnection();
    }

    private void checkConnection() {
        if (shouldReconnect()) {
            attemptReconnect();
        } else {
            sleep();
        }
    }

    private boolean shouldReconnect() {
        return isSocketClosed() || hasTimedOut();
    }

    private boolean isSocketClosed() {
        return socket == null || socket.isClosed();
    }

    private boolean hasTimedOut() {
        if (inactivityTimeLimit <= 0) {
            return false;
        }
        long now = System.currentTimeMillis();
        return lastMessageReceived + inactivityTimeLimit < now;
    }

    private void sleep() {
        try {
            Thread.sleep(CHECK_DELAY);
        } catch (Exception e) {

        }
    }

    private void attemptReconnect() {
        closeConnection();
        try {
            connect();
        } catch (IOException ioe) {
            tickRetryer();
        }
    }

    private void connect() throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        setupReaderAndWriter();
    }

    private void setupReaderAndWriter() throws IOException {
        new SocketReader(socket, this);
        writer = new PrintWriter(socket.getOutputStream(), true);

        listener.onSocketConnected(socket);
    }

    private void tickRetryer() {
        long time = connectionRetryer.getNextDelay();
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // Ignore
        }
    }

    public boolean write(final String msg) {
        if (writer == null) {
            return false;
        }
        writer.println(msg);
        return true;
    }

    private void closeConnection() {
        if (socket == null) {
            return;
        }
        listener.onSocketClosed();
        try {
            socket.close();
        } catch (Exception e) {
            // Ignore
        } finally {
            socket = null;
            writer = null;
        }
    }

    @Override
    public void onMessageReceived(final String line) {
        lastMessageReceived = System.currentTimeMillis();
        listener.onMessageReceived(line);
    }

}
