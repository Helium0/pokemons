package com.example.pokemons.unit;

import com.example.pokemons.entities.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TypeUnitTest {

    private Type type;

    @BeforeEach
    public void setUp() {
        type = new Type();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Electric", "Fire", "Earth"})
    public void providedCorrectNameWithDifferentExamples(String name) {
        //When
        type.setName(name);

        //Then
        assertEquals(name, type.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {" Electric", "  Grass  "})
    public void shouldTrimWhiteSigns(String name) {
        //When
        type.setName(name);

        //Then
        assertEquals(name.trim(), type.getName());
    }

    @Test
    public void providedCorrectTypeLengthName() {
        //Given
        String trainerName = "A" + "c".repeat(99);

        //When
        type.setName(trainerName);

        //Then
        assertEquals(trainerName, type.getName());

    }

    @ParameterizedTest()
    @NullAndEmptySource
    @ValueSource(strings = {" ","    "})
    public void shouldThrowExceptionWhenNameIsInvalid(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> type.setName(name));

        //Then
        assertEquals("Type name should not be null or blank", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"FirE", "FIRE","fire","fIRe"})
    public void shouldThrowExceptionWhenNameHasInvalidCapitalLetter(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> type.setName(name));

        //Then
        assertEquals("Type name only first character should be capital, rest should be lowercase", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenNameIsTooLong() {
        //Given
        String name = "A".repeat(101);

        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> type.setName(name));

        //Then
        assertEquals("Type name length should not be greater than 100 signs", exception.getMessage());
    }
}
