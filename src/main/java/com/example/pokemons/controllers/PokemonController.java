package com.example.pokemons.controllers;

import com.example.pokemons.dtos.CreatePokemonRequest;
import com.example.pokemons.dtos.PokemonDto;
import com.example.pokemons.dtos.PokemonUpdateRequest;
import com.example.pokemons.entities.Pokemon;
import com.example.pokemons.repositories.PokemonRepository;
import com.example.pokemons.services.PokemonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pokemons")
public class PokemonController {

    private final PokemonRepository pokemonRepository;
    private final PokemonService pokemonService;

    @GetMapping
    public List<PokemonDto> getAllPokemons(@RequestParam(required = false, defaultValue = "", name = "typeId") Long pokemonTypeId) {
        List<Pokemon> pokemons;
        if (pokemonTypeId != null) {
            pokemons = pokemonRepository.findByTypeId(pokemonTypeId);
        } else {
            pokemons = pokemonRepository.findAll();
        }
        return pokemons.stream()
                .map(pokemon -> new PokemonDto(pokemon.getId(), pokemon.getName(), pokemon.getDescription(), pokemon.getPower(), pokemon.getType().getName()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonDto> getPokemonById(@PathVariable Long id) {
        var pokemon = pokemonRepository.findById(id).orElse(null);
        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PokemonDto(pokemon.getId(), pokemon.getName(), pokemon.getDescription(), pokemon.getPower(), pokemon.getType().getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PokemonUpdateRequest> updatePokemon(@PathVariable Long id, @RequestBody PokemonUpdateRequest pokemonRequest) {
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

    @PostMapping()
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody CreatePokemonRequest request, UriComponentsBuilder uriBuilder) {
        var pokemon = pokemonService.createPokemon(request);
        var uri = uriBuilder.path("/pokemons/{id}").buildAndExpand(pokemon.getId()).toUri();
      return  ResponseEntity.created(uri).body(pokemon);
    }
}
