package com.saucebot.twitch.message;

public enum Priority {

    LOWEST, LOW, NORMAL, HIGH, VERY_HIGH;

    public boolean isAtleast(final Priority other) {
        return ordinal() >= other.ordinal();
    }

    public boolean isBelowOrEqual(final Priority other) {
        return ordinal() <= other.ordinal();
    }

}
