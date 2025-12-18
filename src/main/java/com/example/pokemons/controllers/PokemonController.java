package com.example.pokemons.controllers;

import com.example.pokemons.dtos.CreatePokemonRequest;
import com.example.pokemons.dtos.PokemonDto;
import com.example.pokemons.dtos.UpdatePokemonRequest;
import com.example.pokemons.entities.Pokemon;
import com.example.pokemons.repositories.PokemonRepository;
import com.example.pokemons.services.PokemonService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/pokemons")
@Validated
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
    public ResponseEntity<PokemonDto> getPokemonById(@PathVariable @Min(value = 1, message = "Pokemon ID must be greater than 0") Long id) {
        var pokemon = pokemonRepository.findById(id).orElse(null);
        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PokemonDto(pokemon.getId(), pokemon.getName(), pokemon.getDescription(), pokemon.getPower(), pokemon.getType().getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatePokemonRequest> updatePokemon(@PathVariable @Min(value = 1, message = "Pokemon ID must be greater than 0") Long id, @RequestBody UpdatePokemonRequest pokemonRequest) {
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
    public ResponseEntity<Object> deletePokemon(@PathVariable @Min(value = 1, message = "Pokemon ID must be greater than 0") Long id) {
        return pokemonRepository.findById(id)
                .map(pokemon -> {
                    pokemonRepository.delete(pokemon);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody CreatePokemonRequest request, UriComponentsBuilder uriBuilder) {
        var pokemon = pokemonService.createPokemon(request);
        var uri = uriBuilder.path("/pokemons/{id}").buildAndExpand(pokemon.getId()).toUri();
        return  ResponseEntity.created(uri).body(pokemon);
    }
}
