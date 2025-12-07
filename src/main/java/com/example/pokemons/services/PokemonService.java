package com.example.pokemons.services;

import com.example.pokemons.custom.exceptions.DuplicateAlreadyExistException;
import com.example.pokemons.entities.Pokemon;
import com.example.pokemons.entities.Type;
import com.example.pokemons.helper.ValidatorHelper;
import com.example.pokemons.repositories.PokemonRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Transactional
@AllArgsConstructor
@Service
public class PokemonService {

    private PokemonRepository pokemonRepository;
    private TypeService typeService;

    public Pokemon createPokemon(String name, BigDecimal power, String desc, String type) {
        var validatedPokemonName = ValidatorHelper.validateAndTrimText(name, "Name");
        var validatedPokemonPower = ValidatorHelper.validateCorrectValue(power, "Power");
        var validatedPokemonType = ValidatorHelper.validateAndTrimText(type, "Type");
        var validatedPokemonDescription = Optional.ofNullable(desc)
                .map(String::trim)
                .orElse("");
        var validatedPokemonDescriptionLength = ValidatorHelper.validateLength(validatedPokemonDescription, "Description");

        Type createdPokemonType = typeService.findOrCreateType(validatedPokemonType);

        if (pokemonRepository.existsByNameAndPowerAndDescriptionAndType(validatedPokemonName, validatedPokemonPower, validatedPokemonDescriptionLength, createdPokemonType)) {
            throw new DuplicateAlreadyExistException(
                    String.format("Pokemon %s with power %s, description: %s and type %s already exists",
                            validatedPokemonName, validatedPokemonPower, validatedPokemonDescription, validatedPokemonType)
            );
        }


        var pokemon = Pokemon.builder()
                .name(validatedPokemonName)
                .power(validatedPokemonPower)
                .description(validatedPokemonDescription)
                .type(createdPokemonType)
                .build();

       return pokemonRepository.save(pokemon);
    }
}
