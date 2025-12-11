package com.example.pokemons.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrainerDto {

    private final Long id;
    private final String name;
    private final String surname;
}
