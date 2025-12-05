package com.example.pokemons.integration.service;

import com.example.pokemons.custom.exceptions.DuplicateAlreadyExistException;
import com.example.pokemons.entities.Pokemon;
import com.example.pokemons.entities.Type;
import com.example.pokemons.repositories.PokemonRepository;
import com.example.pokemons.repositories.TypeRepository;
import com.example.pokemons.services.PokemonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PokemonServiceIntegrationTest {

    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private PokemonService pokemonService;
    @Autowired
    private TypeRepository typeRepository;

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.pokemon.PokemonIntegrationTestData#validPokemonData")
    public void providedValidPokemonDataAndPokemonShouldBeCreated(String name, BigDecimal power, String type) {
        //When
        Pokemon pokemon = pokemonService.createPokemon(name, power, null, type);
        Optional<Pokemon> savedPokemonOnDatabase = pokemonRepository.findById(pokemon.getId());

        //Then
        assertEquals(name, pokemon.getName());
        assertEquals(power, pokemon.getPower());
        assertEquals(type, pokemon.getType().getName());
        assertEquals("", pokemon.getDescription());
        assertEquals(pokemon.getId(), savedPokemonOnDatabase.get().getId());
        assertEquals(pokemon.getName(), savedPokemonOnDatabase.get().getName());
    }

    @Test
    public void similarPokemonButWithDifferentPowerShouldBeCreatedInDatabase() {
        //Given
        var pokemonType = Type.builder().name("Electric").build();
        Type createdType = typeRepository.save(pokemonType);

        var firstPokemon = Pokemon.builder()
                .name("Pikachu")
                .power(new BigDecimal("99.00"))
                .description("Kind pokemon")
                .type(createdType)
                .build();

        var secondPokemon = Pokemon.builder()
                .name("Pikachu")
                .power(new BigDecimal("98.99"))
                .description("Kind pokemon")
                .type(createdType)
                .build();

        //When
        Pokemon createdPokemon = pokemonService.createPokemon(firstPokemon.getName(),
                firstPokemon.getPower(),
                firstPokemon.getDescription(),
                firstPokemon.getType().getName());

        Pokemon secondCreatedPokemon = pokemonService.createPokemon(secondPokemon.getName(),
                secondPokemon.getPower(),
                secondPokemon.getDescription(),
                secondPokemon.getType().getName());

        //Then
        assertNotEquals(createdPokemon.getId(), secondCreatedPokemon.getId());
        assertNotEquals(createdPokemon.getPower(), secondCreatedPokemon.getPower());
        assertEquals(createdPokemon.getName(), secondCreatedPokemon.getName());
    }

    @Test
    public void createDuplicatedPokemonShouldThrowException() {
        //Given
        var customType = Type.builder().name("Electric").build();
        var customPokemon = Pokemon.builder()
                .name("Pikachu")
                .power(new BigDecimal("99.00"))
                .type(customType)
                .build();

        //When
        Pokemon createdPokemon = pokemonService.createPokemon(customPokemon.getName(), customPokemon.getPower(), null, customPokemon.getType().getName());
        DuplicateAlreadyExistException exception = assertThrows(DuplicateAlreadyExistException.class,
                () -> pokemonService.createPokemon(customPokemon.getName(), customPokemon.getPower(), null, customPokemon.getType().getName()));

        //Then
        assertEquals("Pokemon " + createdPokemon.getName()
                        + " with power " + createdPokemon.getPower()
                        + ", description: " + createdPokemon.getDescription()
                        + " and type " + createdPokemon.getType().getName()
                        + " already exists",
                exception.getMessage());
    }

    @Test
    public void providedNullPokemonNameShouldThrowException() {
        //Given
        var pokemon = Pokemon.builder().power(new BigDecimal("99.0")).build();

        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pokemonService.createPokemon(null,pokemon.getPower(),null,null)
        );

        //Then
        assertEquals("Name should not be null or blank", exception.getMessage());
    }
}

