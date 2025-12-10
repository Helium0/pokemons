package com.example.pokemons.testdata.profile;

import com.example.pokemons.entities.Experience;
import com.example.pokemons.entities.Profile;
import com.example.pokemons.entities.Trainer;
import com.example.pokemons.repositories.ProfileRepository;
import com.example.pokemons.services.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceUnitTestData {

    @Mock
    private ProfileRepository profileRepository;
    @InjectMocks
    private ProfileService profileService;

    @Test
    public void shouldCreateProfile() {
        //Given
        var profile = Profile.builder()
                .age(22)
                .country("Japan")
                .experience(Experience.builder().expTime(3L).build())
                .trainer(Trainer.builder().name("Ash").surname("Ketcham").build())
                .build();

        var savedProfile = Profile.builder()
                .id(1L)
                .country(profile.getCountry())
                .experience(profile.getExperience())
                .trainer(profile.getTrainer())
                .build();

        //When
        when(profileRepository.save(any(Profile.class))).thenReturn(savedProfile);
        Profile result = profileService.createProfile(profile);

        //Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Japan", result.getCountry());
        verify(profileRepository).save(any(Profile.class));
    }
}
