package com.saucebot.twitch;

public class User {

    private final String username;

    private String color;

    public User(final String username) {
        this.username = username;
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
