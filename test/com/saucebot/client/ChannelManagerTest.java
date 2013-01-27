package com.saucebot.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.saucebot.client.bot.BotAccount;

public class ChannelManagerTest {

    @Test
    public void testListener() {
        ChannelManager manager = new ChannelManager();
        manager.setChannelListListener(new ChannelListListener() {

            @Override
            public void channelAdded(Channel channel) {
                System.out.println("Added: " + channel);
            }

            @Override
            public void channelRemoved(Channel channel) {
                System.out.println("Removed: " + channel);

            }

        });

        BotAccount bot1 = new BotAccount("FirstBot", "asdf");
        BotAccount bot2 = new BotAccount("SecondBot", "lol");

        List<Channel> chanList = new ArrayList<Channel>(Arrays.asList(new Channel("Ravn", bot1), new Channel("Test",
                bot2), new Channel("Asdfer", bot2)));

        for (Channel initialChannel : chanList) {
            manager.add(initialChannel);
        }

        chanList.remove(2);
        chanList.add(new Channel("Asdfer", bot1));
        manager.set(chanList);

    }

}
