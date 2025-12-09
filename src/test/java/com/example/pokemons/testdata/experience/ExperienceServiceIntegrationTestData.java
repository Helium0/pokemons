package com.example.pokemons.testdata.experience;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ExperienceServiceIntegrationTestData {

    private static final String EXPERIENCE_ERROR = "Experience time should be greater or equal 0";

    public static Stream<Arguments> validIntegerExperience() {
        return Stream.of(
                Arguments.of("Valid integer value: 0", 0L),
                Arguments.of("Valid integer value: 7", 7L),
                Arguments.of("Valid integer value: 10", 10L),
                Arguments.of("Valid integer value: 100", 100L));
    }

    public static Stream<Arguments> invalidIntegerExperience() {
        return Stream.of(
                Arguments.of("Negative experience time", -1L, EXPERIENCE_ERROR),
                Arguments.of("Negative experience time", -49L, EXPERIENCE_ERROR));
    }
}
