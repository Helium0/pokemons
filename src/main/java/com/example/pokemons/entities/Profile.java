package com.example.pokemons.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Data
@ToString
@Table(name = "profiles")
public class Profile {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "country")
    private String country;

    @Column(name = "experience")
    private Long experience;

    @OneToOne
    @JoinColumn(name = "trainer_id")
    @ToString.Exclude
    private Trainer trainer;
}

