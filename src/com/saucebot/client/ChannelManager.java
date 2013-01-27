package com.saucebot.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.saucebot.util.Diff;

public class ChannelManager {

    private Map<String, Channel> channels;

    private ChannelListListener channelListListener = new NullChannelListListener();

    public ChannelManager() {
        channels = new HashMap<String, Channel>();
    }

    public void setChannelListListener(final ChannelListListener listener) {
        if (listener == null) {
            this.channelListListener = new NullChannelListListener();
        } else {
            this.channelListListener = listener;
        }
    }

    public void add(final Channel channel) {
        String name = channel.getIdentifier();
        channels.put(name, channel);

        channelListListener.channelAdded(channel);
    }

    public void remove(final String channelIdentifier) {
        Channel channel = channels.remove(channelIdentifier);

        if (channel != null) {
            channelListListener.channelRemoved(channel);
        }
    }

    public void clear() {
        for (Channel channel : channels.values()) {
            channelListListener.channelRemoved(channel);
        }
        channels.clear();
    }

    public Channel get(final String channelName) {
        return channels.get(channelName.toLowerCase());
    }

    public void set(final List<Channel> channels) {
        if (channels.isEmpty()) {
            clear();
        } else {
            calculateDiff(channels);
        }
    }

    private void calculateDiff(final List<Channel> updatedList) {
        Diff<Channel> diff = Diff.calculate(channels.values(), updatedList);
        if (diff.isSame()) {
            return;
        }

        for (Channel removed : diff.getRemoved()) {
            remove(removed.getIdentifier());
        }

        for (Channel added : diff.getAdded()) {
            add(added);
        }
    }

    private static final class NullChannelListListener implements ChannelListListener {
        @Override
        public void channelAdded(Channel channel) {
        }

        @Override
        public void channelRemoved(Channel channel) {
        }
    }
}
