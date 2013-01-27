package com.saucebot.twitch;

import java.util.HashMap;
import java.util.Map;

public final class Users {

    private static final Map<String, User> users = new HashMap<String, User>();

    public static User get(final String username) {
        String key = username.toLowerCase();
        User user = users.get(key);

        if (user == null) {
            return create(username);
        } else {
            return user;
        }
    }

    public static User create(final String username) {
        User user = new User(username);
        users.put(username.toLowerCase(), user);
        return user;
    }

    public static User updateColorForUser(final String username, final String color) {
        User user = get(username);
        user.setColor(color);
        return user;
    }

}
