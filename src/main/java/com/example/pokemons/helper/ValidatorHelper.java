package com.example.pokemons.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ValidatorHelper {

    private static final String BLANK_OR_NULL_ERROR = "%s should not be null or blank";
    private static final String MAX_LENGTH_ERROR = "%s length should not be greater than 100 signs";
    private static final String CAPITALS_ERROR = "%s only first character should be capital, rest should be lowercase";
    private static final String DOUBLE_MAX_MIN_AMOUNT_ERROR = "%s should not be greater than 100.00 and less than 0.00";
    private static final String DOUBLE_NULL_NAN_ERROR = "%s should not be null or NaN";
    private static final String INTEGER_NULL_ERROR = "%s should not be null";
    private static final String INTEGER_BELOW_ZERO = "%s should be greater or equal 0";

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

    public static BigDecimal validateCorrectValue(Double value, String valueField) {
        if (value == null || value.isNaN()) {
            throw new IllegalArgumentException(String.format(DOUBLE_NULL_NAN_ERROR, valueField));
        }
        if (value > 100.00 || value < 0.00) {
            throw new IllegalArgumentException(String.format(DOUBLE_MAX_MIN_AMOUNT_ERROR, valueField));
        }

        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
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
