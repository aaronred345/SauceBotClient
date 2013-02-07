package com.saucebot.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.saucebot.util.Retryer;

public class AutoSocket implements Runnable {

    private static final long CHECK_DELAY = 2500L;

    private Retryer connectionRetryer;

    private final String host;
    private final int port;

    private Socket socket;
    private PrintWriter writer;

    private volatile boolean running;

    private final SocketListener listener;

    public AutoSocket(final String host, final int port, final SocketListener listener) {
        this.host = host;
        this.port = port;
        this.listener = listener;

        connectionRetryer = new Retryer(500L, 60 * 1000, 1.5);

        startAutoSocket();
    }

    private void startAutoSocket() {
        running = true;
        new Thread(this).start();
    }

    public void close() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            checkConnection();
        }
        closeConnection();
    }

    private void checkConnection() {
        if (socket == null || socket.isClosed()) {
            attemptReconnect();
        } else {
            System.out.print(".");
            sleep();
        }
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

        new SocketReader(socket, listener);
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

}
