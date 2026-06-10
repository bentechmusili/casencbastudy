package com.example.demo.util;

public class TextUtil {

    public static String n(String input) {
        if (input == null || input.isEmpty()) return input;

        return input.substring(0, 1).toUpperCase()
                + input.substring(1).toLowerCase();
    }
    public static String normalizeCountry(String country) {
        if (country == null || country.isBlank()) {
            return country;
        }

        return country.substring(0, 1).toUpperCase()
                + country.substring(1).toLowerCase();
    }
}