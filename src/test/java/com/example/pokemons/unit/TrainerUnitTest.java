package com.example.pokemons.unit;

import com.example.pokemons.entities.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerUnitTest {

    private Trainer trainer;

    @BeforeEach
    public void setUp() {
        trainer = new Trainer();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ash","Misty","Brock", "Jenna",})
    public void providedCorrectTrainerName(String name) {
        //When
        trainer.setName(name);

        //Then
        assertEquals(name, trainer.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {" Ash "," Misty","Brock   "})
    public void shouldTrimTrainerName(String name) {
        //When
        trainer.setName(name);

        //Then
        assertEquals(name.trim(), trainer.getName());
    }

    @Test
    public void providedCorrectTrainerLengthName() {
        //Given
        String trainerName = "A" + "c".repeat(99);

        //When
        trainer.setName(trainerName);

        //Then
        assertEquals(trainerName, trainer.getName());

    }

    @ParameterizedTest
    @ValueSource(strings = {"ASH","FIsty","lesnaR", "alOha"})
    public void shouldThowExceptionWhenTrainerNameHasTooManyCapitalLetters(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setName(name));

        //Then
        assertEquals("Trainer name only first character should be capital, rest should be lowercase", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void shouldThrowExceptionWhenNoTrainerName(String name) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setName(name));

        //Then
        assertEquals("Trainer name should not be null or blank", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTrainerNameIsTooLong() {
        //Given
        String trainerName = "A".repeat(101);

        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setName(trainerName));

        //Then
        assertEquals("Trainer name length should not be greater than 100 signs", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ketcham", "Lesnar", "Jane"})
    public void providedCorrectTrainerSurname(String surname) {
        //When
        trainer.setName(surname);

        //Then
        assertEquals(surname, trainer.getSurname());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ASH "," MISTY","BROCK   "})
    public void shouldTrimTrainerSurname(String surname) {
        //When
        trainer.setSurname(surname);

        //Then
        assertEquals(surname.trim(), trainer.getSurname());
    }


    @ParameterizedTest
    @NullAndEmptySource
    public void shouldThrowExceptionWhenNoTrainerSurname(String surname) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setSurname(surname));

        //Then
        assertEquals("Trainer surname should not be null or blank", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"KEtcham", "LesnaR", "jaNe"})
    public void shoudlThrowExceptionWhenTrainerSurnameHasTooManyCapitalLetters(String surname) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainer.setSurname(surname));

        //Then
        assertEquals("Trainer surname only first character should be capital, rest should be lowercase", exception.getMessage());
    }
}
