package com.example.pokemons.testdata.pokemon;

import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class PokemonPowerTestData {

    public static Stream<Arguments> validPokemonPowerValues() {
        return Stream.of(
                Arguments.of(new BigDecimal("0.00"), "0.00"),
                Arguments.of(new BigDecimal("0.001"), "0.00"),
                Arguments.of(new BigDecimal("100.00"), "100.00"),
                Arguments.of(new BigDecimal("99.99"), "99.99"),
                Arguments.of(new BigDecimal("0.555"), "0.56"),
                Arguments.of(new BigDecimal("1.54"), "1.54"),
                Arguments.of(new BigDecimal("9.56"), "9.56"),
                Arguments.of(new BigDecimal("2.004"), "2.00")
        );
    }

    public static Stream<Arguments> invalidPokemonPowerValues() {
        return Stream.of(
                Arguments.of(new BigDecimal("-0.01"), "Pokemon power should not be greater than 100.00 and less than 0.00"),
                Arguments.of(new BigDecimal("-1.00"), "Pokemon power should not be greater than 100.00 and less than 0.00"),
                Arguments.of(new BigDecimal("100.01"), "Pokemon power should not be greater than 100.00 and less than 0.00"),
                Arguments.of(new BigDecimal("105.00"), "Pokemon power should not be greater than 100.00 and less than 0.00"),
                Arguments.of(null, "Pokemon power should not be null")
        );
    }
}
