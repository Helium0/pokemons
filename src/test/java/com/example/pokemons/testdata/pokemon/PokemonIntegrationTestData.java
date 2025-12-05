package com.example.pokemons.testdata.pokemon;

import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class PokemonIntegrationTestData {

    public static Stream<Arguments> validPokemonData() {
        return Stream.of(
                Arguments.of("Pikachu", new BigDecimal("99.00"), "Kind pokemon", "Electric"),
                Arguments.of("Jigglypuff", new BigDecimal("2.00"), "Very kind pokemon", "Undefined"),
                Arguments.of("Psyduck", new BigDecimal("99.00"), "Kind pokemon", "Water"));
    }
}
