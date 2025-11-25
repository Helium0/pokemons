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
    @ValueSource(strings = {"Electric", "ELECTRIC", "FirE"})
    public void providedCorrectNameWithDifferentExamples(String name) {
        //When
        type.setName(name);
        //Then
        assertEquals(name.toUpperCase(), type.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {" Electric", "  Grass  "})
    public void shouldTrimWhiteSigns(String name) {
        //When
        type.setName(name);
        //Then
        String trimmed = name.trim();
        assertEquals(trimmed.toUpperCase(), type.getName());
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
