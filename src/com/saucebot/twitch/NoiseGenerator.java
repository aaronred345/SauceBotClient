package com.saucebot.twitch;

import java.util.Random;

public class NoiseGenerator {

    private static final char[] noise = ",-_!><#'ดจ?~^+\"".toCharArray();

    private static final int noiseCount = noise.length;

    private static final Random rand = new Random();

    private NoiseGenerator() {

    }

    public static char next() {
        return noise[rand.nextInt(noiseCount)];
    }

}
