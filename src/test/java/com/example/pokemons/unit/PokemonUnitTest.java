package com.example.pokemons.unit;

import com.example.pokemons.entities.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PokemonUnitTest {

    private Pokemon pokemon;

    @BeforeEach
    public void setUp() {
        pokemon = new Pokemon();
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#validStringValues")
    public void providedCorrectPokemonName(String name) {
        //When
        pokemon.setPokemonName(name);

        //Then
        assertEquals(name, pokemon.getName());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#validStringValuesWithWhiteSigns")
    public void shouldTrimPokemonName(String name) {
        //When
        pokemon.setPokemonName(name);

        //Then
        assertEquals(name.trim(), pokemon.getName());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidStringValuesTooManyCapitalsOrNotAny")
    public void shouldThowExceptionWhenPokemonNameHasTooManyCapitalLetters(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pokemon.setPokemonName(name));

        //Then
        assertEquals("Pokemon name only first character should be capital, rest should be lowercase", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidValuesNullOrEmpty")
    public void shouldThrowExceptionWhenNoPokemonName(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pokemon.setPokemonName(name));

        //Then
        assertEquals("Pokemon name should not be null or blank", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidLengthValues")
    public void shouldThrowExceptionWhenPokemonNameIsTooLong(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pokemon.setPokemonName(name));

        //Then
        assertEquals("Pokemon name length should not be greater than 100 signs", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"I ododofo kk ","Text text","Ah cute pokemon   "})
    public void providedPokemonDescriptionWithDifferentForms(String description) {
        //When
        pokemon.setPokemonDescription(description);

        //Then
        assertThat(pokemon.getDescription()).isEqualTo(description != null ? description.trim() : null);
    }

    @ParameterizedTest
    @ValueSource(strings = {" I ododofo kk "," Text text","Ah cute pokemon   "})
    public void shouldTrimPokemonDescription(String description) {
        //When
        pokemon.setPokemonDescription(description);

        //Then
        assertEquals(description.trim(), pokemon.getDescription());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidLengthValues")
    public void shouldThrowExceptionWhenPokemonDescriptionIsTooLong(String description) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pokemon.setPokemonDescription(description));

        //Then
        assertEquals("Pokemon description length should not be greater than 100 signs", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.pokemon.PokemonPowerTestData#validPokemonPowerValues")
    public void providedCorrectPokemonPowerValues(Double power, String expectedPower) {
        //When
        pokemon.setPokemonPower(power);

        //Then
        assertEquals(new BigDecimal(expectedPower), pokemon.getPower());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.pokemon.PokemonPowerTestData#invalidPokemonPowerValues")
    public void shoudlThrowExceptionWhenPokemonPowerHasNoValue(Double power, String expectedMessage) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pokemon.setPokemonPower(power));

        //Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
