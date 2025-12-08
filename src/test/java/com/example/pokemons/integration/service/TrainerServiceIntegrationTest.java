package com.example.pokemons.integration.service;

import com.example.pokemons.entities.Trainer;
import com.example.pokemons.repositories.TrainerRepository;
import com.example.pokemons.services.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TrainerServiceIntegrationTest {

    @Autowired
    private TrainerService trainerService;
    @Autowired
    private TrainerRepository trainerRepository;

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.trainer.TrainerServiceIntegrationTestData#validTrainer")
    public void createTrainerInDatabase(Trainer trainer) {
        //When
        Trainer savedTrainer = trainerService.createTrainer(trainer);

        //Then
        assertNotNull(savedTrainer.getId(), "Trainer ID should be generated");
        assertEquals(trainer.getName(), savedTrainer.getName());
        assertEquals(trainer.getSurname(), savedTrainer.getSurname());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.trainer.TrainerServiceIntegrationTestData#invalidTrainerName")
    public void trainerWithoutNameShouldThrowException(Trainer trainer, String expectedMessage) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainerService.createTrainer(trainer));

        //Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.trainer.TrainerServiceIntegrationTestData#invalidTrainerSurname")
    public void createTrainerWithoutSurnameShouldThrowException(Trainer trainer, String expectedMessage) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> trainerService.createTrainer(trainer));

        //Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void deleteTrainerWithCorrectId() {
        //Given
        var trainer = Trainer.builder()
                .name("Patryk")
                .surname("Prentki")
                .build();

        Trainer createdTrainer = trainerService.createTrainer(trainer);
        assertTrue(trainerRepository.existsById(createdTrainer.getId()));

        //When
        trainerService.deleteTrainer(createdTrainer.getId());

        //Then
        assertFalse(trainerRepository.existsById(createdTrainer.getId()));
    }

    @Test
    public void deleteTrainerWhenIdIsNullShouldThrowException() {
        //When
        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class,
                () -> trainerService.deleteTrainer(null));

        //Then
        assertTrue(exception.getMessage().contains("must not be null"));
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.trainer.TrainerServiceIntegrationTestData#invalidTrainerId")
    public void deleteTrainerWhenIdDoesNotExist(Long invalidId, String expectedMessage) {
        //When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> trainerService.deleteTrainer(invalidId));

        //Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.trainer.TrainerServiceIntegrationTestData#validTrainer")
    public void createTrainerAndUpdateNameAndSurname(Trainer trainer) {
        //Given
        Trainer createdTrainer = trainerService.createTrainer(trainer);
        Optional<Trainer> existTrainer = trainerRepository.findById(createdTrainer.getId());
        assertTrue(existTrainer.isPresent());
        Trainer fromDatabase = existTrainer.get();
        assertEquals(createdTrainer.getName(), fromDatabase.getName());

        //When
        var trainerForUpdate = Trainer.builder().name("Buba").surname("Boba").build();
        Trainer updatedTrainer = trainerService.updateTrainer(existTrainer.get().getId(), trainerForUpdate);

        //Then
        assertEquals("Buba", updatedTrainer.getName());
        assertEquals("Boba", updatedTrainer.getSurname());

    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.trainer.TrainerServiceIntegrationTestData#validTrainer")
    public void shouldUpdateOnlyName(Trainer trainer) {
        //Given
        Trainer savedTrainer = trainerService.createTrainer(trainer);

        //When
        Trainer updatedTrainer = trainerService.updateTrainer(savedTrainer.getId(), Trainer.builder().name("Joanna").build());

        //Then
        assertEquals("Joanna", updatedTrainer.getName());
        assertEquals(savedTrainer.getSurname(), updatedTrainer.getSurname());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.trainer.TrainerServiceIntegrationTestData#validTrainer")
    public void shouldUpdateOnlySurname(Trainer trainer) {
        //Given
        Trainer savedTrainer = trainerService.createTrainer(trainer);

        //When
        Trainer updatedTrainer = trainerService.updateTrainer(savedTrainer.getId(), Trainer.builder().surname("Super").build());

        //Then
        assertEquals("Super", updatedTrainer.getSurname());
        assertEquals(savedTrainer.getName(), updatedTrainer.getName());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.trainer.TrainerServiceIntegrationTestData#validTrainer")
    public void shouldNotUpdateWhenNoValuesProvided(Trainer trainer) {
        //Given
        Trainer savedTrainer = trainerService.createTrainer(trainer);

        //When
        Trainer updatedTrainer = trainerService.updateTrainer(savedTrainer.getId(), Trainer.builder().build());

        //Then
        assertEquals(savedTrainer.getName(), updatedTrainer.getName());
        assertEquals(savedTrainer.getSurname(), updatedTrainer.getSurname());
    }

    @Test
    public void updateTrainerWhenIdIsNullShouldThrowException() {
        //Given
        var trainer = Trainer.builder().name("Patryk").surname("Prentki").build();

        //When
        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class,
                () -> trainerService.updateTrainer(null, trainer));

        //Then
        assertTrue(exception.getMessage().contains("must not be null"));
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.trainer.TrainerServiceIntegrationTestData#invalidTrainerId")
    public void updateTrainerWhenIdDoesNotExistShouldThrowException(Long invalidId, String expectedMessage) {
        //Given
        var trainer = Trainer.builder().name("Patryk").surname("Prentki").build();

        //When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> trainerService.updateTrainer(invalidId, trainer));

        //Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
