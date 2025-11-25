package com.example.pokemons.helper;

public class ValidatorHelper {

    private static final String BLANK_OR_NULL_ERROR = "%s should not be null or blank";
    private static final String MAX_LENGTH_ERROR = "%s length should not be greater than 100 signs";
    private static final String CAPITALS_ERROR = "%s only first character should be capital, rest should be lowercase";

    public static String validateAndTrim(String someWord, String fieldName) {
        if (someWord == null || someWord.isBlank()) {
            throw new IllegalArgumentException(String.format(BLANK_OR_NULL_ERROR, fieldName));
        }
        String trimmedWord = someWord.trim();
        if (trimmedWord.length() > 100) {
            throw new IllegalArgumentException(String.format(MAX_LENGTH_ERROR, fieldName));
        }
        if (!trimmedWord.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*$")) {
            throw new IllegalArgumentException(String.format(CAPITALS_ERROR, fieldName));
        }
        return trimmedWord.trim();
    }
}
