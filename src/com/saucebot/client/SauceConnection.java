package com.saucebot.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.saucebot.client.emit.Emit;
import com.saucebot.client.emit.EmitHandler;
import com.saucebot.client.emit.EmitUtils;
import com.saucebot.net.AutoSocket;
import com.saucebot.net.SocketListener;

public class SauceConnection implements SocketListener {

    private AutoSocket socket;

    private SauceListener listener;

    private Map<String, Method> emitHandlers;

    public SauceConnection(final String host, final int port) {
        socket = new AutoSocket(host, port, this);
        emitHandlers = new HashMap<String, Method>();
    }

    public void registerHandlers(final SauceListener object) {
        this.listener = object;

        for (Method method : object.getClass().getMethods()) {
            EmitHandler handlerAnnotation = method.getAnnotation(EmitHandler.class);
            if (handlerAnnotation != null) {
                String type = handlerAnnotation.value();
                emitHandlers.put(type, method);
            }
        }
    }

    public void connect() {
        socket.open();
    }

    public void close() {
        socket.close();
    }

    @Override
    public void onMessageReceived(final String json) {
        Emit emit = EmitUtils.parse(json);
        String cmd = emit.getCmd();
        Method handler = emitHandlers.get(cmd);
        if (handler != null) {
            invokeHandlerMethod(handler, emit);
        } else {
            processUnhandledEmit(emit);
        }
    }

    private void invokeHandlerMethod(final Method handler, final Emit emit) {
        try {
            handler.invoke(listener, emit);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.err.println("Error invoking emit handler method: " + e + " (for " + emit + ")");
        }
    }

    private void processUnhandledEmit(final Emit emit) {
        listener.onEmit(emit);
    }

    @Override
    public void onSocketClosed() {
        listener.onDisconnect();
    }

    @Override
    public void onSocketConnected(final Socket socket) {
        listener.onConnect();
    }

    public void write(final Emit emit) {
        String json = EmitUtils.serialize(emit);
        socket.write(json);
    }

}
