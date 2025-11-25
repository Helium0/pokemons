package com.example.pokemons.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "types")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "type")
    @ToString.Exclude
    private List<Pokemon> pokemonSet = new ArrayList<>();

    public void addPokemon(Pokemon pokemon) {
        getPokemonSet().add(pokemon);
        pokemon.setType(this);
    }

}
