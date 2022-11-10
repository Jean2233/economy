package com.github.jean2233.economy.util;

import java.util.Random;

public class RandomGenerator {
    private static final Random RANDOM = new Random();

    public static double generate(double min, double max) {
        return Math.max(RANDOM.nextInt((int) max), min);
    }

    public static double generate(double max) {
        return RANDOM.nextInt((int) max);
    }

    public static int generate(int min, int max) {
        return Math.max(RANDOM.nextInt(max), min);
    }

    public static int generate(int max) {
        return RANDOM.nextInt(max);
    }
}