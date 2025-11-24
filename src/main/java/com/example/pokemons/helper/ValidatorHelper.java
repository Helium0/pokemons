package com.example.pokemons.helper;

public class ValidatorHelper {

    public static String validateAndTrim(String someValue, String fieldName) {
        if (someValue == null || someValue.isBlank()) {
            throw new IllegalArgumentException(String.format("%s should not be null or blank", fieldName));
        }
        return someValue.trim().toUpperCase();
    }

}
