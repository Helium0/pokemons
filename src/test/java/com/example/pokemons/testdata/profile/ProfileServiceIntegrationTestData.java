package com.example.pokemons.testdata.profile;

import com.example.pokemons.entities.Experience;
import com.example.pokemons.entities.Profile;
import com.example.pokemons.entities.Trainer;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ProfileServiceIntegrationTestData {

    private static final Trainer VALID_TRAINER = Trainer.builder().name("Ash").surname("Ketcham").build();
    private static final Experience EXPERIENCE_TIME = Experience.builder().expTime(3L).build();

    public static Stream<Arguments> invalidProfileCountryData() {
        return Stream.of(
                Arguments.of("Field country has too many characters",
                        Profile.builder().age(19)
                                .country("A".repeat(102))
                                .experience(EXPERIENCE_TIME).trainer(VALID_TRAINER).build(),
                        "Country length should not be greater than 100 signs"));
    }
}
