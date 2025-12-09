package com.example.pokemons.testdata.common;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class CommonStringTestData {


    public static Stream<String> validStringValues() {
        return Stream.of("Asj","Pikachu","Brock","Squirrle","Lesnar","Jenna","Fire","Earth","Ketcham","A"+("c".repeat(99)));
    }

    public static Stream<String> validStringValuesWithWhiteSigns() {
        return Stream.of(" Ash"," Pikachu ","Lesnar   ","  Earth   ");
    }

    public static Stream<String> invalidStringValuesTooManyCapitalsOrNotAny() {
        return Stream.of("ASH","PikaChu","brocK","lesnar","eaRth");
    }

    public static Stream<Arguments> invalidValuesNullOrEmpty() {
        return Stream.of(
                Arguments.of("Null value", null),
                Arguments.of("Blank value", ""),
                Arguments.of("Blank value with white spaces", "       "));
    }

    public static Stream<Arguments> invalidLengthValues() {
        return Stream.of(
                Arguments.of("Invalid length characters: 102", "A".repeat(101)),
                Arguments.of("Invalid length characters: 121", "B".repeat(120)));
    }


}
