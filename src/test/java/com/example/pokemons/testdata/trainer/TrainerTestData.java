package com.example.pokemons.testdata.trainer;

import com.example.pokemons.entities.Trainer;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TrainerTestData {

    private static final String NAME_NULL_OR_BLANK_ERROR = "Name should not be null or blank";
    private static final String SURNAME_NULL_OR_BLANK_ERROR = "Surname should not be null or blank";

    public static Stream<Arguments> validTrainer() {
        return Stream.of(
                Arguments.of(Trainer.builder().name("Patryk").surname("Tester").build()),
                Arguments.of(Trainer.builder().name("Joanna").surname("Testerka").build()));
    }

    public static Stream<Arguments> invalidTrainerName() {
        return Stream.of(
                Arguments.of(Trainer.builder().name("").surname("Tester").build(), NAME_NULL_OR_BLANK_ERROR),
                Arguments.of(Trainer.builder().surname("Tester").name("     ").build(), NAME_NULL_OR_BLANK_ERROR),
                Arguments.of(Trainer.builder().surname("Tester").name(null).build(), NAME_NULL_OR_BLANK_ERROR));
    }

    public static Stream<Arguments> invalidTrainerSurname() {
        return Stream.of(
                Arguments.of(Trainer.builder().name("Patryk").surname("").build(), SURNAME_NULL_OR_BLANK_ERROR),
                Arguments.of(Trainer.builder().name("Patryk").surname("             ").build(), SURNAME_NULL_OR_BLANK_ERROR),
                Arguments.of(Trainer.builder().name("Patryk").surname(null).build(), SURNAME_NULL_OR_BLANK_ERROR));
    }

    public static Stream<Arguments> invalidTrainerId() {
        return Stream.of(
                Arguments.of(-1L, "Trainer with id -1 do not exist"),
                Arguments.of(0L, "Trainer with id 0 do not exist"),
                Arguments.of(1000L, "Trainer with id 1000 do not exist"));
    }
}
