package com.saucebot.test;

import java.nio.file.Paths;

import com.saucebot.client.SauceManager;

public class TestClient {

    private TestClient() {
        SauceManager manager = new SauceManager(Paths.get("C:\\Users\\Ravn\\Desktop\\sauceconfig.json"));

    }

    public static void main(final String[] args) throws Exception {
        new TestClient();
    }

}
