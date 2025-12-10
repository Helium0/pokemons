package com.example.pokemons.testdata.trainer;

import com.example.pokemons.entities.Trainer;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TrainerServiceIntegrationTestData {

    private static final String NAME_NULL_OR_BLANK_ERROR = "Name should not be null or blank";
    private static final String SURNAME_NULL_OR_BLANK_ERROR = "Surname should not be null or blank";

    public static Stream<Arguments> validTrainer() {
        return Stream.of(
                Arguments.of("Trainer name: Ash and surname: Ketcham", Trainer.builder().name("Ash").surname("Ketcham").build()),
                Arguments.of("Trainer name: Brook and surname: Lesnar", Trainer.builder().name("Brook").surname("Lesnar").build()),
                Arguments.of("Trainer name has white spaces at the end and surname at the beginning", Trainer.builder().name("Ash   ").surname("  Lesnar").build()),
                Arguments.of("Trainer name has white spaces at the beginning and surname at the end", Trainer.builder().name("   Brook ").surname("Lesnar   ").build()));
    }

    public static Stream<Arguments> invalidTrainerName() {
        return Stream.of(
                Arguments.of("Trainer name is blank", Trainer.builder().name("").surname("Ketcham").build(), NAME_NULL_OR_BLANK_ERROR),
                Arguments.of("Trainer surname is blank and has white spaces", Trainer.builder().surname("Ketcham").name("     ").build(), NAME_NULL_OR_BLANK_ERROR),
                Arguments.of("Trainer surname is null", Trainer.builder().surname("Ketcham").name(null).build(), NAME_NULL_OR_BLANK_ERROR));
    }

    public static Stream<Arguments> invalidTrainerSurname() {
        return Stream.of(
                Arguments.of("Trainer surname is blank", Trainer.builder().name("Ash").surname("").build(), SURNAME_NULL_OR_BLANK_ERROR),
                Arguments.of("Trainer surname is blank and has white spaces", Trainer.builder().name("Ash").surname("             ").build(), SURNAME_NULL_OR_BLANK_ERROR),
                Arguments.of("Trainer surname is null", Trainer.builder().name("Ash").surname(null).build(), SURNAME_NULL_OR_BLANK_ERROR));
    }

    public static Stream<Arguments> invalidTrainerId() {
        return Stream.of(
                Arguments.of("Negative trainer ID", -1L, "Trainer with id -1 do not exist"),
                Arguments.of("Trainer ID equal 0", 0L, "Trainer with id 0 do not exist"),
                Arguments.of("Trainer ID does not exist", 1000L, "Trainer with id 1000 do not exist"));
    }
}
