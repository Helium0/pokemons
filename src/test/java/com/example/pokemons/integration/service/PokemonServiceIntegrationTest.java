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
import java.util.List;
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
    @MethodSource(value = "com.example.pokemons.testdata.pokemon.PokemonServiceIntegrationTestData#validPokemonData")
    public void providedValidPokemonDataAndPokemonShouldBeCreated(String name, BigDecimal power, String desc, Type type) {
        //When
        Pokemon pokemon = pokemonService.createPokemon(name, power, desc, type.getName());
        Optional<Pokemon> savedPokemonOnDatabase = pokemonRepository.findById(pokemon.getId());

        //Then
        assertEquals(name, pokemon.getName());
        assertEquals(power, pokemon.getPower());
        assertEquals(type.getName(), pokemon.getType().getName());
        assertEquals(desc, pokemon.getDescription());
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

    @Test
    public void findByTypeShouldReturnOnlyPokemonsWithGivenType() {
        //Given
        var savedElectricType = typeRepository.save(Type.builder().name("Electric").build());
        var savedWaterType = typeRepository.save(Type.builder().name("Water").build());

        //When
        pokemonService.createPokemon("Raichu",
                new BigDecimal("99.00"),
                null,
                savedElectricType.getName());

        pokemonService.createPokemon("Pikachu",
                new BigDecimal("99.60"),
                null,
                savedElectricType.getName());

        pokemonService.createPokemon("Psyduck",
                new BigDecimal("50.00"),
                null,
                savedWaterType.getName());

        List<Pokemon> waterPokemons = pokemonRepository.findByType(savedWaterType);
        List<Pokemon> electricPokemons = pokemonRepository.findByType(savedElectricType);
        List<Pokemon> allPokemons = pokemonRepository.findAll();

        //Then
        assertEquals(1, waterPokemons.size());
        assertEquals(2, electricPokemons.size());
        assertEquals(3, allPokemons.size());
    }

    @Test
    public void testCustomDeleteMethod() {
        //Given
        var waterType = typeRepository.save(Type.builder().name("Water").build());

        //When
        var createdPokemon = pokemonService.createPokemon("Psyduck",
                new BigDecimal("50.00"),
                null,
                waterType.getName());

        Optional<Pokemon> foundedPokemon = pokemonRepository.findById(createdPokemon.getId());

        assertTrue(foundedPokemon.isPresent());
        assertEquals(createdPokemon.getName(), foundedPokemon.get().getName());

        pokemonRepository.deletePokemonByNameAndPowerAndType(createdPokemon.getName(),createdPokemon.getPower(),createdPokemon.getType());

        //Then
        Optional<Pokemon> deletedPokemon = pokemonRepository.findById(createdPokemon.getId());
        assertFalse(deletedPokemon.isPresent());
    }

    @Test
    public void testCustomFindByTypeAndGroupByPowerMethod() {
        //Given
        var waterType = typeRepository.save(Type.builder().name("Water").build());

        var firstWaterPokemon = pokemonService.createPokemon("Psyduck",
                new BigDecimal("50.00"),
                null,
                waterType.getName());

        var secondWaterPokemon = pokemonService.createPokemon("Star",
                new BigDecimal("52.00"),
                null,
                waterType.getName());

        var thirddWaterPokemon = pokemonService.createPokemon("Magikarp",
                new BigDecimal("65.00"),
                null,
                waterType.getName());

        //When
        List<Pokemon> createdPokemonList = pokemonRepository.findByType(waterType);
        assertEquals(3, createdPokemonList.size());

        List<Pokemon> sortedByPowerPokemonList = pokemonRepository.findByTypeOrderByPowerDesc(waterType);

        //Then
        assertEquals(new BigDecimal("65.00"), sortedByPowerPokemonList.get(0).getPower());
        assertEquals(thirddWaterPokemon.getName(), sortedByPowerPokemonList.get(0).getName());
        assertEquals(thirddWaterPokemon.getType(), sortedByPowerPokemonList.get(0).getType());
        assertEquals(firstWaterPokemon.getType(), sortedByPowerPokemonList.get(2).getType());
        assertEquals(firstWaterPokemon.getName(), sortedByPowerPokemonList.get(2).getName());
    }
}

