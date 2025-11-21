package com.example.pokemons.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@ToString
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

    public void addProfile(Trainer trainerProfile) {
        var prof = Profile.builder()
                .trainer(trainerProfile)
                .age(35)
                .country("Poland")
                .build();
        trainerProfile.setProfile(prof);
    }

}
