package com.example.pokemons.unit;

import com.example.pokemons.entities.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class TypeUnitTest {

    private Type type;

    @BeforeEach
    public void setUp() {
        type = new Type();
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#validStringValues")
    public void providedCorrectTypeName(String name) {
        //When
        type.setTypeName(name);

        //Then
        assertEquals(name, type.getName());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#validStringValuesWithWhiteSigns")
    public void shouldTrimTypeName(String name) {
        //When
        type.setTypeName(name);

        //Then
        assertEquals(name.trim(), type.getName());
    }

    @ParameterizedTest()
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidValuesNullOrEmpty")
    public void shouldThrowExceptionWhenNameIsInvalid(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> type.setTypeName(name));

        //Then
        assertEquals("Type name should not be null or blank", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidStringValuesTooManyCapitalsOrNotAny")
    public void shouldThrowExceptionWhenNameHasInvalidCapitalLetter(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> type.setTypeName(name));

        //Then
        assertEquals("Type name only first character should be capital, rest should be lowercase", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidLengthValues")
    public void shouldThrowExceptionWhenNameIsTooLong(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> type.setTypeName(name));

        //Then
        assertEquals("Type name length should not be greater than 100 signs", exception.getMessage());
    }
}
