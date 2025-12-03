package com.example.pokemons.services;

import com.example.pokemons.components.TrainerUpdater;
import com.example.pokemons.entities.Trainer;
import com.example.pokemons.helper.ValidatorHelper;
import com.example.pokemons.repositories.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;

    private final TrainerUpdater trainerUpdater;


    public Trainer createTrainer(Trainer trainer) {
        trainer.setTrainerName(ValidatorHelper.validateAndTrimText(trainer.getName(),"Name"));
        trainer.setTrainerSurname(ValidatorHelper.validateAndTrimText(trainer.getSurname(),"Surname"));
        return trainerRepository.save(trainer);
    }

    public void deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Trainer with id %s do not exist", id)));
//        if (!trainer.getPokemon().isEmpty()) {
//            throw new IllegalArgumentException("Can not delete trainer with assigned pokemons");  -- TO DO !!
//        }
        trainerRepository.delete(trainer);
    }

    public Trainer updateTrainer(Long id, Trainer updatedTrainer) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Trainer with id %s do not exist", id)));

        trainerUpdater.updateTrainerFields(trainer, updatedTrainer);

        return trainerRepository.save(trainer);
    }
}
