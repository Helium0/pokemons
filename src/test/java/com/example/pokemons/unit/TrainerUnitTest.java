package com.example.pokemons.unit;

import com.example.pokemons.entities.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerUnitTest {

    private Trainer trainer;

    @BeforeEach
    public void setUp() {
        trainer = new Trainer();
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#validStringValues")
    public void providedCorrectTrainerName(String name) {
        //When
        trainer.setName(name);

        //Then
        assertEquals(name, trainer.getName());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#validStringValuesWithWhiteSigns")
    public void shouldTrimTrainerName(String name) {
        //When
        trainer.setTrainerName(name);

        //Then
        assertEquals(name.trim(), trainer.getName());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidStringValuesTooManyCapitalsOrNotAny")
    public void shouldThowExceptionWhenTrainerNameHasTooManyCapitalLetters(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setTrainerName(name));

        //Then
        assertEquals("Trainer name only first character should be capital, rest should be lowercase", exception.getMessage());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidValuesNullOrEmpty")
    public void shouldThrowExceptionWhenNoTrainerName(String testName, String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setTrainerName(name));

        //Then
        assertEquals("Trainer name should not be null or blank", exception.getMessage());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidLengthValues")
    public void shouldThrowExceptionWhenTrainerNameIsTooLong(String testName, String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setTrainerName(name));

        //Then
        assertEquals("Trainer name length should not be greater than 100 signs", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#validStringValues")
    public void providedCorrectTrainerSurname(String surname) {
        //When
        trainer.setTrainerSurname(surname);

        //Then
        assertEquals(surname, trainer.getSurname());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#validStringValuesWithWhiteSigns")
    public void shouldTrimTrainerSurname(String surname) {
        //When
        trainer.setTrainerSurname(surname);

        //Then
        assertEquals(surname.trim(), trainer.getSurname());
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidValuesNullOrEmpty")
    public void shouldThrowExceptionWhenNoTrainerSurname(String testName, String surname) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setTrainerSurname(surname));

        //Then
        assertEquals("Trainer surname should not be null or blank", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidStringValuesTooManyCapitalsOrNotAny")
    public void shoudlThrowExceptionWhenTrainerSurnameHasTooManyCapitalLetters(String surname) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setTrainerSurname(surname));

        //Then
        assertEquals("Trainer surname only first character should be capital, rest should be lowercase", exception.getMessage());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#invalidLengthValues")
    public void shouldThrowExceptionWhenTrainerSurnameIsTooLong(String testName, String surname) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setTrainerSurname(surname));

        //Then
        assertEquals("Trainer surname length should not be greater than 100 signs", exception.getMessage());
    }
}
