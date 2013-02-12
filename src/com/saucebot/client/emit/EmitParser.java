package com.saucebot.client.emit;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.saucebot.client.Channel;
import com.saucebot.client.ChannelManager;
import com.saucebot.client.Sauce;
import com.saucebot.client.bot.BotAccount;
import com.saucebot.client.bot.BotAccountManager;
import com.saucebot.twitch.message.BotMessage;

public class EmitParser {

    private BotAccountManager accountManager;
    private ChannelManager channelManager;

    public EmitParser(final BotAccountManager accountManager, final ChannelManager channelManager) {
        this.accountManager = accountManager;
        this.channelManager = channelManager;
    }

    public void parseChannelEmit(final Emit emit) {
        JsonArray channelArray = emit.asArray();

        List<Channel> channels = new ArrayList<Channel>();

        for (JsonElement channelElement : channelArray) {
            parseChannelJsonObject(channels, channelElement.getAsJsonObject());
        }
        channelManager.set(channels);
    }

    private void parseChannelJsonObject(final List<Channel> channelList, final JsonObject channelObject) {
        String name = channelObject.get("name").getAsString();
        int status = channelObject.get("status").getAsInt();
        String bot = null;

        if (!channelObject.get("bot").isJsonNull()) {
            bot = channelObject.get("bot").getAsString();
        }

        BotAccount account = accountManager.getBotAccount(bot);
        Channel chan = new Channel(name, account);

        if (status == Sauce.STATUS_ENABLED) {
            channelList.add(chan);
        }
    }

    public BotMessage parseSayEmit(final Emit emit) {
        String msg = emit.get("msg");
        return botMessageForEmit(emit, msg);
    }

    public BotMessage parseTimeoutEmit(final Emit emit) {
        String user = emit.get("user");
        int time = Sauce.DEFAULT_TIMEOUT_IN_SECONDS;

        if (emit.hasElement("time")) {
            time = emit.getElement("time").getAsInt();
        }

        return botMessageForEmit(emit, "/timeout " + user + " " + time);
    }

    public BotMessage parseBanEmit(final Emit emit) {
        String user = emit.get("user");

        return botMessageForEmit(emit, "/ban " + user);
    }

    public BotMessage parseUnbanEmit(final Emit emit) {
        String user = emit.get("user");

        return botMessageForEmit(emit, "/unban " + user);
    }

    private BotMessage botMessageForEmit(final Emit emit, final String message) {
        return new BotMessage(message, EmitUtils.getPriorityForType(emit.getCmd()));
    }

}
