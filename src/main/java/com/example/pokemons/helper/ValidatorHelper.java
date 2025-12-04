package com.example.pokemons.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ValidatorHelper {

    private static final String BLANK_OR_NULL_ERROR = "%s should not be null or blank";
    private static final String MAX_LENGTH_ERROR = "%s length should not be greater than 100 signs";
    private static final String CAPITALS_ERROR = "%s only first character should be capital, rest should be lowercase";
    private static final String BIG_DECIMAL_MAX_MIN_AMOUNT_ERROR = "%s should not be greater than 100.00 and less than 0.00";
    private static final String BIG_DECIMAL_NULL_ERROR = "%s should not be null";
    private static final String INTEGER_NULL_ERROR = "%s should not be null";
    private static final String INTEGER_BELOW_ZERO = "%s should be greater or equal 0";
    private static final String REGEX_VALIDATION = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*$";

    public static String validateAndTrimText(String someWord, String fieldName) {
        if (someWord == null || someWord.isBlank()) {
            throw new IllegalArgumentException(String.format(BLANK_OR_NULL_ERROR, fieldName));
        }
        String trimmedText = trimText(someWord);
        if (trimmedText.length() > 100) {
            throw new IllegalArgumentException(String.format(MAX_LENGTH_ERROR, fieldName));
        }
        if (!someWord.matches(REGEX_VALIDATION)) {
            throw new IllegalArgumentException(String.format(CAPITALS_ERROR, fieldName));
        }

        return trimmedText;
    }

    private static String trimText(String text) {
        return text.trim();
    }

    public static String validateLength(String someText, String fieldName) {
        if (someText == null ) {
            return null;
        }
        if (someText.isBlank()) {
            return "";
        }
        if (someText.length() > 100) {
            throw new IllegalArgumentException(String.format(MAX_LENGTH_ERROR, fieldName));
        }

        return someText.trim();
    }

    public static BigDecimal validateCorrectValue(BigDecimal value, String valueField) {
        if (value == null) {
            throw new IllegalArgumentException(String.format(BIG_DECIMAL_NULL_ERROR, valueField));
        }
        if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(new BigDecimal(100)) > 0) {
            throw new IllegalArgumentException(String.format(BIG_DECIMAL_MAX_MIN_AMOUNT_ERROR, valueField));
        }

        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static Integer validateCorrectInteger(Integer integer, String integerField) {
        if (integer == null) {
            throw new IllegalArgumentException(String.format(INTEGER_NULL_ERROR, integerField));
        }
        if (integer < 0) {
            throw new IllegalArgumentException(String.format(INTEGER_BELOW_ZERO, integerField));
        }

        return integer;
    }
}
