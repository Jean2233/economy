package com.github.jean2233.economy.util;

import java.text.DecimalFormat;

public class Formats {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.#");

    public static String apply(double value) {
        return DECIMAL_FORMAT.format(value);
    }
}
