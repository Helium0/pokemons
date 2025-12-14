package com.example.pokemons.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePokemonRequest {

    private final String name;
    private final String description;
    private final BigDecimal power;
    private final String typeName;
}
