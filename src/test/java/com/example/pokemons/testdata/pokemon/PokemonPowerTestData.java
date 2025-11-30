package com.example.pokemons.testdata.pokemon;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

public class PokemonPowerTestData {

    public static Stream<Arguments> validPokemonPowerValues() {
        return Stream.of(
                Arguments.of(0.00, "0.00"),
                Arguments.of(0.001, "0.00"),
                Arguments.of(100.00, "100.00"),
                Arguments.of(99.99, "99.99"),
                Arguments.of(0.555, "0.56"),
                Arguments.of(1.54, "1.54"),
                Arguments.of(9.56, "9.56"),
                Arguments.of(2.004, "2.00")
        );
    }

    public static Stream<Arguments> invalidPokemonPowerValues() {
        return Stream.of(
                Arguments.of(-0.01, "Pokemon power should not be greater than 100.00 and less than 0.00"),
                Arguments.of(-1.00, "Pokemon power should not be greater than 100.00 and less than 0.00"),
                Arguments.of(100.01, "Pokemon power should not be greater than 100.00 and less than 0.00"),
                Arguments.of(105.00, "Pokemon power should not be greater than 100.00 and less than 0.00"),
                Arguments.of(Double.NaN, "Pokemon power should not be null or NaN"),
                Arguments.of(null, "Pokemon power should not be null or NaN")
        );
    }
}
