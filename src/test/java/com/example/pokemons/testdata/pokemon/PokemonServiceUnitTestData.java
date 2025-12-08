package com.example.pokemons.testdata.pokemon;

import com.example.pokemons.entities.Type;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class PokemonServiceUnitTestData {

    private static final Type ELECTRIC_TYPE = Type.builder().name("Electric").build();
    private static final Type EARTH_TYPE = Type.builder().name("Earth").build();
    private static final Type WATER_TYPE = Type.builder().name("Water").build();
    private static final Type FIRE_TYPE = Type.builder().name("Fire").build();
    private static final Type NULL_TYPE_NAME = Type.builder().name(null).build();
    private static final Type BLANK_TYPE_NAME = Type.builder().name("").build();


    public static Stream<Arguments> validPokemonData() {
        return Stream.of(
                Arguments.of("Pikachu", new BigDecimal("90.00"), "Kind pokemon", ELECTRIC_TYPE),
                Arguments.of("Psyduck", new BigDecimal("50.00"), "Very kind pokemon", WATER_TYPE),
                Arguments.of("Psyduck", new BigDecimal("50.00"), "Very kind pokemon", WATER_TYPE),
                Arguments.of("Gomez", new BigDecimal("15.50"), "Most pokemon", EARTH_TYPE));
    }
    public static Stream<Arguments> invalidPokemonData() {
        return Stream.of(
                Arguments.of(null, new BigDecimal("50.00"), null, WATER_TYPE, "Name should not be null or blank"),
                Arguments.of("", new BigDecimal("90.00"), null, WATER_TYPE, "Name should not be null or blank"),
                Arguments.of("           ", new BigDecimal("90.00"), null, WATER_TYPE, "Name should not be null or blank"),
                Arguments.of("a".repeat(100), new BigDecimal("56.00"), null, WATER_TYPE, "Name only first character should be capital, rest should be lowercase"),
                Arguments.of("A" + "b".repeat(100), new BigDecimal("56.00"), null, WATER_TYPE, "Name length should not be greater than 100 signs"),
                Arguments.of("A".repeat(100), new BigDecimal("56.00"), null, WATER_TYPE, "Name only first character should be capital, rest should be lowercase"),
                Arguments.of("Pikachu", new BigDecimal("100.01"), "Kind pokemon", ELECTRIC_TYPE, "Power should not be greater than 100.00 and less than 0.00"),
                Arguments.of("Pikachu", new BigDecimal("100.005"), "Kind pokemon", ELECTRIC_TYPE, "Power should not be greater than 100.00 and less than 0.00"),
                Arguments.of("Pikachu", new BigDecimal("-0.01"), "Kind pokemon", ELECTRIC_TYPE, "Power should not be greater than 100.00 and less than 0.00"),
                Arguments.of("Pikachu", new BigDecimal("-0.001"), "Kind pokemon", ELECTRIC_TYPE, "Power should not be greater than 100.00 and less than 0.00"),
                Arguments.of("Pikachu", new BigDecimal("35.00"), "Kind pokemon".repeat(40), ELECTRIC_TYPE, "Description length should not be greater than 100 signs"),
                Arguments.of("Pikachu", new BigDecimal("90.00"), "Kind pokemon", NULL_TYPE_NAME, "Type should not be null or blank"),
                Arguments.of("Pikachu", new BigDecimal("90.00"), "Kind pokemon", BLANK_TYPE_NAME, "Type should not be null or blank"));
    }
}
