package com.example.pokemons.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdatePokemonRequest {

    private final String description;
    private final BigDecimal power;
}
