package com.saucebot.twitch;

import com.saucebot.twitch.message.BotMessage;

public interface MessageQueueListener {

    public void onNextMessage(BotMessage message);

}
