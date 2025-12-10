package com.example.pokemons.integration.service;

import com.example.pokemons.entities.Experience;
import com.example.pokemons.entities.Profile;
import com.example.pokemons.entities.Trainer;
import com.example.pokemons.repositories.ProfileRepository;
import com.example.pokemons.services.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProfileServiceIntegrationTest {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileService profileService;

    @Test
    public void profileShouldContainTrainer() {
        //Given
        var trainer = Trainer.builder().name("Patryk").surname("Prentki").build();
        var experience = Experience.builder().expTime(3L).build();
        var profile = Profile.builder().experience(experience).age(30).country("Poland").trainer(trainer).build();

        //When
        Profile savedProfile = profileRepository.save(profile);

        //Then
        assertNotNull(savedProfile.getTrainer());
        assertNotNull(savedProfile.getTrainer().getId());
        assertEquals(savedProfile.getId(),(savedProfile.getTrainer().getId()));
        assertEquals(trainer.getName(),(savedProfile.getTrainer().getName()));
        assertEquals(trainer.getSurname(),(savedProfile.getTrainer().getSurname()));
    }

    @Test
    public void saveProfileWithoutTrainerShouldThrowException() {
        //Given
        var experience = Experience.builder().expTime(3L).build();
        var profile = Profile.builder().experience(experience).age(30).country("Poland").trainer(null).build();

        //When
        assertThrows(JpaSystemException.class,
                () -> profileRepository.save(profile));

        //Then
        assertEquals(0, profileRepository.count());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "com.example.pokemons.testdata.profile.ProfileServiceIntegrationTestData#invalidProfileCountryData")
    public void profileWithInvalidCountryDataShouldThrowException(String testName, Profile profile, String expectedMessage) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> profileService.createProfile(profile));

        //Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
