package com.example.pokemons.controllers;

import com.example.pokemons.dtos.PokemonDto;
import com.example.pokemons.dtos.PokemonRequest;
import com.example.pokemons.entities.Pokemon;
import com.example.pokemons.repositories.PokemonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pokemons")
public class PokemonController {

    private PokemonRepository pokemonRepository;

    @GetMapping
    public List<PokemonDto> getAllPokemons(@RequestParam(required = false, defaultValue = "", name = "typeId") Long pokemonTypeId) {
        List<Pokemon> pokemons;
        if (pokemonTypeId != null) {
            pokemons = pokemonRepository.findByTypeId(pokemonTypeId);
        } else {
            pokemons = pokemonRepository.findAll();
        }
        return pokemons.stream()
                .map(pokemon -> new PokemonDto(pokemon.getId(), pokemon.getName(), pokemon.getDescription(), pokemon.getPower(), pokemon.getType().getId()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonDto> getPokemonById(@PathVariable Long id) {
        var pokemon = pokemonRepository.findById(id).orElse(null);
        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PokemonDto(pokemon.getId(), pokemon.getName(), pokemon.getDescription(), pokemon.getPower(), pokemon.getType().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PokemonRequest> updatePokemon(@PathVariable Long id, @RequestBody PokemonRequest pokemonRequest) {
        var pokemon = pokemonRepository.findById(id).orElse(null);
        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }

        pokemon.setPokemonDescription(pokemonRequest.getDescription());
        pokemon.setPokemonPower(pokemonRequest.getPower());

        pokemonRepository.save(pokemon);
        return ResponseEntity.ok(pokemonRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePokemon(@PathVariable Long id) {
        var pokemon = pokemonRepository.findById(id).orElse(null);
        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }

        pokemonRepository.delete(pokemon);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }
}
