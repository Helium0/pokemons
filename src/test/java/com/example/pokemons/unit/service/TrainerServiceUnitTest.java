package com.example.pokemons.unit.service;

import com.example.pokemons.components.TrainerUpdater;
import com.example.pokemons.entities.Trainer;
import com.example.pokemons.repositories.TrainerRepository;
import com.example.pokemons.services.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceUnitTest {

    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainerUpdater trainerUpdater;
    @InjectMocks
    private TrainerService trainerService;

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.trainer.TrainerServiceUnitTestData#trainerDataWithWhiteSpaces")
    public void createTrainerWithWhiteSpacesShouldTrimAndValidateNameAndSurname(String name, String surname) {
        //Given
        var inputTrainer = Trainer.builder()
                .name(name)
                .surname(surname)
                .build();

        var expectedTrainer = Trainer.builder()
                .id(1L)
                .name("Ash")
                .surname("Ketcham")
                .build();

        //When
        when(trainerRepository.save(any(Trainer.class)))
                .thenReturn(expectedTrainer);
        var outputTrainer = trainerService.createTrainer(inputTrainer);

        //Then
        assertEquals(expectedTrainer.getName(), outputTrainer.getName());
        assertEquals(expectedTrainer.getId(), outputTrainer.getId());
        verify(trainerRepository).save(any(Trainer.class));
    }

    @Test
    public void deleteTrainerWhenTrainerExistsShouldDeleteTrainer() {
        //Given
        var trainerId = 1L;
        var existingTrainer = Trainer.builder()
                .id(trainerId)
                .name("Ash")
                .surname("Ketcham")
                .build();

        //When
        when(trainerRepository.findById(existingTrainer.getId())).thenReturn(Optional.of(existingTrainer));
        doNothing().when(trainerRepository).delete(existingTrainer);
        trainerService.deleteTrainer(existingTrainer.getId());

        //Then
        verify(trainerRepository).findById(existingTrainer.getId());
        verify(trainerRepository).delete(existingTrainer);
    }

    @Test
    public void deleteTrainerWhichDoesntExistShouldThrowException() {
        //Given
        var trainerId = 9999L;

        //When
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> trainerService.deleteTrainer(trainerId));

        assertEquals("Trainer with id " + trainerId + " do not exist", exception.getMessage());
        verify(trainerRepository).findById(trainerId);
        verify(trainerRepository, never()).delete(any());
    }

    @Test
    public void updateTrainer() {
        //Given
        var trainerId = 2L;
        var existTrainer = Trainer.builder()
                .id(trainerId)
                .name("Ash")
                .surname("Ketcham")
                .build();

        var trainerUpdated = Trainer.builder()
                .id(trainerId)
                .name("Brock")
                .surname("Lesnar")
                .build();

        //When
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(existTrainer));
        when(trainerRepository.save(any(Trainer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doAnswer( invocation -> {
                    Trainer target = invocation.getArgument(0);
                    Trainer source = invocation.getArgument(1);
                    if (target.getName() != null) {
                        target.setTrainerName(source.getName().trim());
                    }
                    if (target.getSurname() != null) {
                        target.setTrainerSurname(source.getSurname().trim());
                    }
                    return null;
                }).when(trainerUpdater).updateTrainerFields(any(Trainer.class), any(Trainer.class));
        trainerService.updateTrainer(trainerId, trainerUpdated);

        //Then
        assertEquals("Brock", trainerUpdated.getName());
        assertEquals("Lesnar", trainerUpdated.getSurname());
        verify(trainerRepository).findById(trainerId);
        verify(trainerRepository).save(any(Trainer.class));
        verify(trainerUpdater).updateTrainerFields(any(Trainer.class), any(Trainer.class));
    }
}
