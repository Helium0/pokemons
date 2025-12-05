package com.example.pokemons.repositories;

import com.example.pokemons.entities.Pokemon;
import com.example.pokemons.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    boolean existsByNameAndPowerAndDescriptionAndType(String name, BigDecimal power, String desc, Type type);
    List<Pokemon> findByNameAndPowerAndType(String name, BigDecimal power, Type type);

}
