package com.example.pokemons.testdata.pokemon;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class PokemonControllerTestData {

    private final static String POKEMON_ID_VALUE_MESSAGE = "Pokemon ID must be greater than 0";

    public static Stream<Arguments> invalidNumberTestData() {
        return Stream.of(
                Arguments.of("Invalid value: -1", -1, POKEMON_ID_VALUE_MESSAGE),
                Arguments.of("Invalid value: -100", -100, POKEMON_ID_VALUE_MESSAGE),
                Arguments.of("Invalid value: -0", 0, POKEMON_ID_VALUE_MESSAGE));
    }

    public static Stream<Arguments> invalidTypeNumberData() {
        return Stream.of(
                Arguments.of("Invalid type data: text", "a", "Invalid parameter: id. Provided: a when expected a number"),
                Arguments.of("Invalid type data: text", "test", "Invalid parameter: id. Provided: test when expected a number"),
                Arguments.of("Invalid type data: sppecial characters", "!@", "Invalid parameter: id. Provided: !@ when expected a number"));
    }
}