package com.saucebot.client.emit;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.saucebot.twitch.message.Priority;

public class EmitUtils {

    private static final Map<String, Priority> emitPriorities;
    static {
        emitPriorities = new HashMap<String, Priority>();

        emitPriorities.put(EmitType.BAN, Priority.VERY_HIGH);
        emitPriorities.put(EmitType.UNBAN, Priority.VERY_HIGH);

        emitPriorities.put(EmitType.TIMEOUT, Priority.HIGH);

        emitPriorities.put(EmitType.MESSAGE, Priority.NORMAL);
    }

    private static final Gson gson = new Gson();

    public static Emit parse(final String json) {
        return gson.fromJson(json, Emit.class);
    }

    public static String serialize(final Emit emit) {
        return gson.toJson(emit);
    }

    public static Priority getPriorityForType(final String type) {
        Priority priority = emitPriorities.get(type);
        if (priority == null) {
            return Priority.NORMAL;
        } else {
            return priority;
        }
    }

}
