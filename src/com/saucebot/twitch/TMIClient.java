package com.saucebot.twitch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.saucebot.net.Connection;
import com.saucebot.net.ConnectionListener;
import com.saucebot.twitch.message.IrcCode;
import com.saucebot.twitch.message.IrcMessage;
import com.saucebot.twitch.message.Message;
import com.saucebot.twitch.message.SystemMessage;
import com.saucebot.util.IRCUtils;

public class TMIClient implements ConnectionListener {

    private final String channelName;

    private Set<User> ops;

    private Connection connection;

    private final String accountName;
    private final String accountPassword;

    private Map<IrcCode, Method> codeHandlers;

    public TMIClient(final String channelName, final String accountName, final String accountPassword) {
        this.channelName = channelName;
        this.accountName = accountName;
        this.accountPassword = accountPassword;

        setupHandlers();

        ops = new HashSet<User>();

        connection = new Connection(Twitch.getAddressForChannel(channelName), Twitch.CHAT_PORT);
        connection.setConnectionListener(this);
        connection.connect();
    }

    private void setupHandlers() {
        codeHandlers = new HashMap<IrcCode, Method>();
        for (Method method : getClass().getMethods()) {
            IrcHandler handlerAnnotation = method.getAnnotation(IrcHandler.class);
            if (handlerAnnotation != null) {
                codeHandlers.put(handlerAnnotation.value(), method);
            }
        }

    }

    public void close() {
        connection.close();
    }

    public String getUsername() {
        return accountName;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getIrcChannelName() {
        return '#' + getChannelName();
    }

    public Set<User> getOps() {
        return ops;
    }

    public boolean isOp(final User user) {
        return ops.contains(user);
    }

    public void addOp(final User user) {
        ops.add(user);
    }

    public void removeOp(final User user) {
        ops.remove(user);
    }

    @Override
    public void onConnected() {
        String name = getUsername();

        send("PASS", this.accountPassword);
        send("NICK", name);
        send("USER", name, 8, '*', name);

    }

    @Override
    public void onDisconnected() {

    }

    @IrcHandler(IrcCode.Endofmotd)
    public void handleEndofmotd(final IrcMessage message) {
        String chan = getIrcChannelName();

        send("JOIN", chan);
        send("JTVROOMS", chan);
        send("JTVCLIENT", chan);
    }

    @IrcHandler(IrcCode.Privmsg)
    public void handlePrivmsg(final IrcMessage message) {
        String channel = message.getArg(0);
        String username = message.getUser();
        String text = message.getArg(1);

        if ("jtv".equals(username)) {
            handleSystemMessage(text);
            return;
        }

        User user = Users.get(username);
        if (isOp(user)) {
            username = '@' + username;
        }

        System.out.printf("%s %s <%s> %s\n", channel, user.getColor(), username, text);
    }

    private void handleSystemMessage(final String line) {
        SystemMessage message = SystemMessage.parse(line);
        if (message.getType().isSystem()) {
            processMessage(message);
        }
    }

    @IrcHandler(IrcCode.Usercolor)
    public void handleUsercolor(final SystemMessage message) {
        String username = message.getArg(0);
        String color = message.getArg(1);

        Users.get(username).setColor(color);
    }

    @IrcHandler(IrcCode.Join)
    public void handleJoin(final IrcMessage message) {
        String user = message.getUser();
        if (user.equalsIgnoreCase(this.accountName)) {
            send("WHO", '#' + this.channelName);
        }
    }

    @IrcHandler(IrcCode.Mode)
    public void handleMode(final IrcMessage message) {
        String mode = message.getArg(1);

        switch (mode) {

        case "+o": // op
            addOp(Users.get(message.getArg(2)));
            break;

        case "-o": // deop
            removeOp(Users.get(message.getArg(2)));
            break;

        default:
            System.out.println("Unknown mode: " + message.getRawLine());
        }
    }

    @IrcHandler(IrcCode.Ping)
    public void handlePing(final IrcMessage message) {
        sendRaw("PONG");
    }

    @IrcHandler(IrcCode.Unknown)
    public void handleOther(final Message message) {
        IrcCode type = message.getType();
        System.out.println(type.name() + ": " + message);
    }

    @Override
    public void onMessageReceived(final String line) {
        IrcMessage message = IrcMessage.parse(line);
        processMessage(message);
    }

    private void processMessage(final Message message) {
        IrcCode type = message.getType();
        Method method = codeHandlers.get(type);
        if (method != null) {
            invokeHandlerMethod(message, method);
        } else {
            handleOther(message);
        }
    }

    private void invokeHandlerMethod(final Message message, final Method method) {
        try {
            method.invoke(this, message);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.err.println("Error invoking handler method: " + e);
        }
    }

    private void send(final String code, final Object... args) {
        connection.send(IRCUtils.format(code, args));
    }

    private void sendRaw(final String line) {
        connection.send(line + '\r');
    }

    public void sendMessage(final String line) {
        send("PRIVMSG", getIrcChannelName(), line);
    }

}
