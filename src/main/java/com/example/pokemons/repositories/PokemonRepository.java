package com.example.pokemons.repositories;

import com.example.pokemons.entities.Pokemon;
import com.example.pokemons.entities.Type;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    boolean existsByNameAndPowerAndDescriptionAndType(String name, BigDecimal power, String desc, Type type);

    @Query(value = "SELECT p FROM Pokemon p WHERE p.type = :type")
    List<Pokemon> findByType(@Param("type") Type type);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM Pokemon WHERE name = :name AND power = :power AND type = :type")
    void deletePokemonByNameAndPowerAndType(@Param("name") String name, @Param("power") BigDecimal power, @Param("type") Type type);

    List<Pokemon> findByTypeOrderByPowerDesc(Type type);

    List<Pokemon> findByTypeId(Long id);
}
