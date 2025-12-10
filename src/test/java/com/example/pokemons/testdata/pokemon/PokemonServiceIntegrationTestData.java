package com.example.pokemons.testdata.pokemon;

import com.example.pokemons.entities.Type;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class PokemonServiceIntegrationTestData {

    private static final Type ELECTRIC_TYPE = Type.builder().name("Electric").build();
    private static final Type EARTH_TYPE = Type.builder().name("Earth").build();
    private static final Type WATER_TYPE = Type.builder().name("Water").build();

    public static Stream<Arguments> validPokemonData() {
        return Stream.of(
                Arguments.of("Pokemon: Pikachu", "Pikachu", new BigDecimal("90.00"), "Kind pokemon", ELECTRIC_TYPE),
                Arguments.of("Pokemon: Psyduck", "Psyduck", new BigDecimal("50.00"), "Very kind pokemon", WATER_TYPE),
                Arguments.of("Pokemon: Gomez", "Gomez", new BigDecimal("15.50"), "Most pokemon", EARTH_TYPE));
    }
}
