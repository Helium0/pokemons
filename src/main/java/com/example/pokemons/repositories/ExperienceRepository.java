package com.example.pokemons.repositories;

import com.example.pokemons.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    Optional<Experience> findByExpTime(Integer expTime);
}
