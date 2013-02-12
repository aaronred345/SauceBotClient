package com.saucebot.client;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SauceBot {

    public static void main(final String[] args) {
        if (args.length < 1) {
            System.err
                    .println("Invalid usage.\nUse: java " + SauceBot.class.getCanonicalName() + " <config file path>");
            System.exit(1);
        }

        Path configPath = Paths.get(args[0]);
        if (!Files.exists(configPath)) {
            System.err.println("Invalid config file \"" + configPath + "\"");
            System.exit(1);
        }

        new SauceManager(configPath);
    }

}
