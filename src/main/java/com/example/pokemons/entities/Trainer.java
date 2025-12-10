package com.example.pokemons.entities;

import com.example.pokemons.helper.ValidatorHelper;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainers")
public class Trainer {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToOne(mappedBy = "trainer")
    private Profile profile;

    @ManyToMany()
    @JoinTable(
            name = "pokedex",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "pokemon_id")
    )
    private Set<Pokemon> pokemon = new HashSet<>();

    public void setTrainerName(String name) {
        this.name = ValidatorHelper.validateAndTrimText(name, "Trainer name");
    }

    public void setTrainerSurname(String surname) {
        this.surname = ValidatorHelper.validateAndTrimText(surname, "Trainer surname");
    }
}
