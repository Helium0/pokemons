package com.example.pokemons.entities;

import com.example.pokemons.helper.ValidatorHelper;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience")
    private Experience experience;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    @MapsId
    @ToString.Exclude
    private Trainer trainer;

    public void setCountry(String country) {
        this.country = ValidatorHelper.validateLength(country, "Trainer country");
    }
}

