package com.saucebot.twitch;

import java.util.HashSet;
import java.util.Set;

public class User {

    private final String username;

    private String color;

    private final Set<SpecialUserType> userTypes;

    public User(final String username) {
        this.username = username;

        userTypes = new HashSet<SpecialUserType>();
    }

    public String getColor() {
        return color;
    }

    public String getUsername() {
        return username;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public void addSpecialUserType(final SpecialUserType type) {
        userTypes.add(type);
    }

    public boolean isSpecialUserType(final SpecialUserType type) {
        return userTypes.contains(type);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;

        if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User[" + getUsername() + "]";
    }

}
