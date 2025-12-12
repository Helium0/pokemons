package com.example.pokemons.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PokemonRequest {

    private final String description;
    private final BigDecimal power;
}
