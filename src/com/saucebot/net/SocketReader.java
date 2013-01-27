package com.saucebot.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketReader implements Runnable {

    private BufferedReader reader;
    private MessageListener listener;
    private Socket socket;

    public SocketReader(final Socket socket, final MessageListener listener) {
        this.socket = socket;
        this.listener = listener;

        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                listener.onMessageReceived(line);
            }
        } catch (IOException ioe) {
            // Ignore and skip to finally-block
        } finally {
            closeSocket();
        }
    }

    private void closeSocket() {
        if (socket == null) {
            return;
        }
        try {
            socket.close();
        } catch (IOException e) {

        } finally {
            socket = null;
        }
    }

}
