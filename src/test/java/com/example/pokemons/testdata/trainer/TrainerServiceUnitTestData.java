package com.example.pokemons.testdata.trainer;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TrainerServiceUnitTestData {

    public static Stream<Arguments> trainerDataWithWhiteSpaces() {
        return Stream.of(
                Arguments.of(" Ash", "Ketcham"),
                Arguments.of("Ash    ", "Ketcham "),
                Arguments.of("    Ash    ", "     Ketcham "));
    }
}
