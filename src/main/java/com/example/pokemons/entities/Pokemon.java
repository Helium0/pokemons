package com.example.pokemons.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "pokemons")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "power")
    private BigDecimal power;

    @ManyToOne()
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToMany(mappedBy = "pokemon")
    private Set<Trainer> trainer = new HashSet<>();

    public void setPokemonName(String name) {
        this.name = name;
    }

    public void setPokemonDescription(String description) {
        this.description = description;
    }

    public void setPokemonPower(BigDecimal power) {
        this.power = power;
    }

    public void setPokemonToType(Trainer providedTrainer) {
        trainer.add(providedTrainer);

    }

}
