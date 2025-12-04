package com.example.pokemons.entities;

import com.example.pokemons.helper.ValidatorHelper;
import jakarta.persistence.*;
import lombok.*;

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
    private Integer expTime;

    public void setExperienceTime(Integer expTime) {
        this.expTime = ValidatorHelper.validateCorrectInteger(expTime, "Experience time");
    }
}
