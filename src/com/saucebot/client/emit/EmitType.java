package com.saucebot.client.emit;

public interface EmitType {

    // Client -> Server
    public static final String REGISTER = "register";
    public static final String MESSAGE = "msg";
    public static final String PRIVATE_MESSAGE = "pm";
    public static final String GET = "get";
    public static final String UPDATE = "upd";
    public static final String INTERFACE = "int";

    // Server -> Client
    public static final String SAY = "say";
    public static final String BAN = "ban";
    public static final String UNBAN = "unban";
    public static final String TIMEOUT = "timeout";
    public static final String CHANNELS = "channels";
    public static final String ERROR = "error";

}
