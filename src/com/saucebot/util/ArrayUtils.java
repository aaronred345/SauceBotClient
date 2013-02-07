package com.saucebot.util;

public class ArrayUtils {

    public static String[] slice(final String[] array, final int startIndex) {
        int length = array.length;
        String[] spliced = new String[length - startIndex];
        for (int i = startIndex; i < length; i++) {
            spliced[i - startIndex] = array[i];
        }
        return spliced;
    }

}
