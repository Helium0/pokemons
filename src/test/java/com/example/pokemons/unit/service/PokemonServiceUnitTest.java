package com.example.pokemons.unit.service;

import com.example.pokemons.custom.exceptions.DuplicateAlreadyExistException;
import com.example.pokemons.entities.Pokemon;
import com.example.pokemons.entities.Type;
import com.example.pokemons.repositories.PokemonRepository;
import com.example.pokemons.services.PokemonService;
import com.example.pokemons.services.TypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceUnitTest {

    @Mock
    private PokemonRepository pokemonRepository;
    @Mock
    private TypeService typeService;
    @InjectMocks
    private PokemonService pokemonService;

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.pokemon.PokemonServiceUnitTestData#validPokemonData")
    public void createPokemonWithValidData(String name, BigDecimal power, String desc, Type type) {
        //Given
        var savedPokemon = Pokemon.builder()
                .id(1L)
                .name(name)
                .power(power)
                .description(desc)
                .type(type)
                .build();

        //When
        when(typeService.findOrCreateType(type.getName())).thenReturn(type);
        when(pokemonRepository.existsByNameAndPowerAndDescriptionAndType(
                name, power, desc, type))
                .thenReturn(false);
        when(pokemonRepository.save(any(Pokemon.class)))
                .thenReturn(savedPokemon);

        Pokemon createdPokemon = pokemonService.createPokemon(
                name,
                power,
                desc,
                type.getName());

        //Then
        assertThat(createdPokemon).isNotNull();
        assertThat(createdPokemon.getId()).isEqualTo(1L);
        assertThat(createdPokemon.getName()).isEqualTo(name);
        assertThat(createdPokemon.getPower()).isEqualTo(power);
        assertThat(createdPokemon.getDescription()).isEqualTo(desc);
        assertThat(createdPokemon.getType()).isEqualTo(type);
        verify(typeService).findOrCreateType(type.getName());
        verify(pokemonRepository).existsByNameAndPowerAndDescriptionAndType(name, power, desc, type);
        verify(pokemonRepository).save(any(Pokemon.class));
    }

    @Test
    public void createPokemonWhenExistShouldThrowException() {
        //Given
        var waterType = Type.builder().name("Water").build();

        //When
        when(typeService.findOrCreateType("Water"))
                .thenReturn(waterType);
        when(pokemonRepository.existsByNameAndPowerAndDescriptionAndType(
                eq("Psyduck"),
                eq(new BigDecimal("55.09")),
                eq(""),
                eq(waterType))).thenReturn(true);

        //Then
        assertThrows(DuplicateAlreadyExistException.class,
                () -> pokemonService.createPokemon("Psyduck",
                        new BigDecimal("55.09"),
                        null,
                        "Water"));

        verify(typeService).findOrCreateType("Water");
        verify(pokemonRepository).existsByNameAndPowerAndDescriptionAndType(
                eq("Psyduck"),
                eq(new BigDecimal("55.09")),
                eq(""),
                eq(waterType));
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.pokemon.PokemonServiceUnitTestData#invalidPokemonData")
    public void createPokemonWithLackOfDataOrInvalidDataShouldThrowException(String name, BigDecimal power, String desc, Type type, String expectedMessage) {
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pokemonService.createPokemon(name, power, desc, type.getName()));

        //Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
