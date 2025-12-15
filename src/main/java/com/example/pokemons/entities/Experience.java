package com.example.pokemons.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "exp_time")
    private Long expTime;

    @OneToMany(mappedBy = "experience")
    private List<Profile> profiles;

    public void setExperienceTime(Long expTime) {
        this.expTime = expTime;
    }
}
