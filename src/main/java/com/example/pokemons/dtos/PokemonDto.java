package com.example.pokemons.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PokemonDto {

    private final Long id;
    private final String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String description;
    private final BigDecimal power;
    private final Long type_id;
}
