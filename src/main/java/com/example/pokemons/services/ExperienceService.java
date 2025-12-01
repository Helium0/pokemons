package com.example.pokemons.services;

import com.example.pokemons.custom.exceptions.ExperienceAlreadyExistException;
import com.example.pokemons.entities.Experience;
import com.example.pokemons.helper.ValidatorHelper;
import com.example.pokemons.repositories.ExperienceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Transactional
    public Experience createExperience(Integer expTime) {
          Integer validatedExperience = ValidatorHelper.validateCorrectInteger(expTime, "Experience time");

          Optional<Experience> experienceExist = experienceRepository.findByExpTime(validatedExperience);
          if (experienceExist.isPresent()) {
              throw new ExperienceAlreadyExistException("Experience time: " + validatedExperience + " already exists in DB on ID: " + experienceExist.get().getId());
          }

          Experience experience = new Experience();
          experience.setExperienceTime(validatedExperience);
          return experienceRepository.save(experience);
    }

}
