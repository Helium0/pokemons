package com.example.pokemons.integration.controler;

import com.example.pokemons.controllers.PokemonController;
import com.example.pokemons.entities.Pokemon;
import com.example.pokemons.entities.Type;
import com.example.pokemons.repositories.PokemonRepository;
import com.example.pokemons.services.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(PokemonController.class)
public class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @MockBean
    private PokemonRepository pokemonRepository;

    @Test
    public void shoudlReturnPokemonObject() throws Exception {
        //Given
        var pokemon = new Pokemon();
        pokemon.setId(1L);
        pokemon.setPokemonName("Pikachu");
        pokemon.setPokemonDescription("Electric pokemon");
        pokemon.setPokemonPower(new BigDecimal("99.50"));

        var type = new Type();
        type.setName("Electric");
        pokemon.setType(type);

        //When
        when(pokemonRepository.findById(1L)).thenReturn(Optional.of(pokemon));

        //Then
        mockMvc.perform(get("http://localhost:8080/pokemons/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Pikachu"))
                .andExpect(jsonPath("$.description").value("Electric pokemon"))
                .andExpect(jsonPath("$.power").value("99.5"))
                .andExpect(jsonPath("$.typeName").value("Electric"));
    }
}
