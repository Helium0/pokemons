package com.example.pokemons.entities;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "exp_time")
    private Integer integer;
}
