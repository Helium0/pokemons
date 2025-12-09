package com.example.pokemons.integration.service;

import com.example.pokemons.custom.exceptions.DuplicateAlreadyExistException;
import com.example.pokemons.entities.Experience;
import com.example.pokemons.repositories.ExperienceRepository;
import com.example.pokemons.services.ExperienceService;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ExperienceServiceIntegrationTest {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceService experienceService;

    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "com.example.pokemons.testdata.experience.ExperienceServiceIntegrationTestData#validIntegerExperience")
    public void providedValidExperienceTimeValues(String testName, Long expTime) {
        //When
        Experience experience = experienceService.createExperience(expTime);

        //Then
        assertNotNull(experience);
        assertNotNull(experience.getId());
        assertEquals(expTime, experience.getExpTime());

        Optional<Experience> check = experienceRepository.findById(experience.getId());

        assertTrue(check.isPresent());
        assertEquals(check.get().getExpTime(), expTime);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "com.example.pokemons.testdata.experience.ExperienceServiceIntegrationTestData#invalidIntegerExperience")
    public void invalidExperienceTimeShouldThrowException(String testName, Long expTime, String expectedMessage) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> experienceService.createExperience(expTime));

        //Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void experienceTimeAsANullShouldThrowException() {
        //Given
        Long expTime = null;

        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> experienceService.createExperience(expTime));

        //Then
        assertEquals("Experience time should not be null", exception.getMessage());
    }

    @Test
    public void providedExperienceTimeWhichExistsInDatabaseShouldThrowException() {
        //Given
        Long expTime = 999L;

        //When
        Experience experience = experienceService.createExperience(expTime);
        assertNotNull(experience.getId());
        assertNotNull(experience.getExpTime());
        assertEquals(expTime, experience.getExpTime());

        DuplicateAlreadyExistException exception = assertThrows(DuplicateAlreadyExistException.class,
                () -> experienceService.createExperience(expTime));

        //Then
        assertEquals("Experience time: " + expTime + " already exists in DB on ID: " + experience.getId(), exception.getMessage());
    }
}
