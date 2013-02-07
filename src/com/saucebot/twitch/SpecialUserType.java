package com.saucebot.twitch;

public enum SpecialUserType {

    admin, staff, turbo, subscriber(true);

    private boolean channelSpecific;

    SpecialUserType(final boolean channelSpecific) {
        this.channelSpecific = channelSpecific;
    }

    SpecialUserType() {
        this(false);
    }

    public boolean isChannelSpecific() {
        return channelSpecific;
    }

}
