package com.saucebot.client.mock;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.saucebot.config.ConfigObject;
import com.saucebot.config.ConfigParser;

public class MockConfigParser implements ConfigParser {

    private Map<String, ConfigObject> root;

    public MockConfigParser() {
        root = new HashMap<String, ConfigObject>();
    }

    public void put(final String key, final ConfigObject value) {
        root.put(key, value);
    }

    @Override
    public ConfigObject parse(final Path path) {
        return new ConfigObject(root);
    }

}
