package com.example.pokemons.entities;

import com.example.pokemons.helper.ValidatorHelper;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private Long id;

    @Column(name = "name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "type")
    @ToString.Exclude
    private List<Pokemon> pokemonSet = new ArrayList<>();


    public void setTypeName(String name) {
       this.name = ValidatorHelper.validateAndTrimText(name, "Type name");
    }

    public void addPokemon(Pokemon pokemon) {
        getPokemonSet().add(pokemon);
        pokemon.setType(this);
    }

}
