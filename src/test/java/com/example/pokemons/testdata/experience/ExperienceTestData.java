package com.example.pokemons.testdata.experience;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ExperienceTestData {

    public static Stream<Integer> validIntegerExperience() {
        return Stream.of(0,7, 10, 100);
    }

    public static Stream<Arguments> invalidIntegerExperience() {
        return Stream.of(
                Arguments.of(-1, "Experience time should be greater or equal 0"),
                Arguments.of(-49, "Experience time should be greater or equal 0"));
    }
}
