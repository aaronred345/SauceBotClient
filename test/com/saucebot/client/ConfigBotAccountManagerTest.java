package com.saucebot.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.saucebot.client.bot.BotAccount;
import com.saucebot.client.bot.ConfigBotAccountManager;
import com.saucebot.client.mock.MockConfigParser;
import com.saucebot.config.ConfigObject;
import com.saucebot.config.ConfigParser;

public class ConfigBotAccountManagerTest {

    @Test
    public void testLoadFromConfigParser() {
        ConfigBotAccountManager manager = new ConfigBotAccountManager(getMockAccountParser(), Paths.get("/"));
        checkMockAccounts(manager);
    }

    private ConfigParser getMockAccountParser() {
        MockConfigParser parser = new MockConfigParser();
        parser.put("accounts", getMockAccountObject());
        return parser;
    }

    @Test
    public void testLoadFromConfigObject() {
        ConfigBotAccountManager manager = new ConfigBotAccountManager(getMockAccountObject());
        checkMockAccounts(manager);
    }

    @Test
    public void testGetDefault() {
        ConfigBotAccountManager manager = new ConfigBotAccountManager(getMockAccountObject());
        assertFalse(manager.hasBotAccount(null));
        assertNull(manager.getBotAccount(null));

        manager.setDefaultAccount(new BotAccount("Test", "asdf"));

        assertTrue(manager.hasBotAccount(null));
        assertEquals("Test", manager.getBotAccount(null).getUsername());
    }

    private void checkMockAccounts(ConfigBotAccountManager manager) {
        BotAccount saucebot = manager.getBotAccount("SauceBot");
        assertNotNull(saucebot);
        assertEquals("SauceBot", saucebot.getUsername());
        assertEquals("asdf", saucebot.getPassword());

        BotAccount beardbot = manager.getBotAccount("BEARDBOT");
        assertNotNull(beardbot);
        assertEquals("BeardBot", beardbot.getUsername());
        assertEquals("1234dfa", beardbot.getPassword());

        assertTrue(manager.hasBotAccount("drunkbot"));
    }

    private ConfigObject getMockAccountObject() {
        Map<String, ConfigObject> accounts = new HashMap<String, ConfigObject>();
        accounts.put("SauceBot", new ConfigObject("asdf"));
        accounts.put("DrunkBot", new ConfigObject("erwger"));
        accounts.put("BeardBot", new ConfigObject("1234dfa"));
        return new ConfigObject(accounts);

    }

}
