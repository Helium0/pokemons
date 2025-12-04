package com.example.pokemons.components;

import com.example.pokemons.entities.Trainer;
import com.example.pokemons.helper.ValidatorHelper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Component
public class TrainerUpdater {

    public void updateTrainerFields(Trainer target, Trainer source) {
        updateIfNull(target::setTrainerName, source.getName(), "Name");
        updateIfNull(target::setTrainerSurname, source.getSurname(), "Surname");
    }

    private void updateIfNull(Consumer<String> text, String value, String fieldName) {
        Optional.ofNullable(value)
                .map(someValue -> ValidatorHelper.validateAndTrimText(someValue, fieldName))
                .ifPresent(text);
    }
}