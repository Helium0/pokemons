package com.example.pokemons.services;

import com.example.pokemons.custom.exceptions.DuplicateAlreadyExistException;
import com.example.pokemons.entities.Experience;
import com.example.pokemons.helper.ValidatorHelper;
import com.example.pokemons.repositories.ExperienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public Experience createExperience(Integer expTime) {
          var validatedExperience = ValidatorHelper.validateCorrectInteger(expTime, "Experience time");

          Optional<Experience> experienceExist = experienceRepository.findByExpTime(validatedExperience);
          if (experienceExist.isPresent()) {
              throw new DuplicateAlreadyExistException("Experience time: " + validatedExperience + " already exists in DB on ID: " + experienceExist.get().getId());
          }

          Experience experience = new Experience();
          experience.setExperienceTime(validatedExperience);
          return experienceRepository.save(experience);
    }

}
