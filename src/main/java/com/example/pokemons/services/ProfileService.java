package com.example.pokemons.services;

import com.example.pokemons.entities.Profile;
import com.example.pokemons.helper.ValidatorHelper;
import com.example.pokemons.repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile createProfile(Profile profile) {
        profile.setCountry(ValidatorHelper.validateLength(profile.getCountry(), "Country"));
        return profileRepository.save(profile);
    }
}
