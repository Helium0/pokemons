package com.example.pokemons.integration.controler;

import com.example.pokemons.controllers.PokemonController;
import com.example.pokemons.dtos.PokemonDto;
import com.example.pokemons.dtos.UpdatePokemonRequest;
import com.example.pokemons.entities.Pokemon;
import com.example.pokemons.entities.Type;
import com.example.pokemons.repositories.PokemonRepository;
import com.example.pokemons.repositories.TypeRepository;
import com.example.pokemons.services.PokemonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PokemonController.class)
public class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PokemonService pokemonService;
    @MockBean
    private PokemonRepository pokemonRepository;
    @MockBean
    private TypeRepository typeRepository;

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
        mockMvc.perform(get("/pokemons/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Pikachu"))
                .andExpect(jsonPath("$.description").value("Electric pokemon"))
                .andExpect(jsonPath("$.power").value("99.5"))
                .andExpect(jsonPath("$.typeName").value("Electric"));
    }

    @Test
    public void shouldReturnNotFoundWhenPokemonDoesntExist() throws Exception {
        //Given
        var nonExistingPokemonId = 999L;

        //When
        when(pokemonRepository.findById(nonExistingPokemonId)).thenReturn(Optional.empty());

        //Then
        mockMvc.perform(get("/pokemons/{id}", nonExistingPokemonId))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("com.example.pokemons.testdata.pokemon.PokemonControllerTestData#invalidTypeNumberData")
    public void shouldReturnBadRequestWhenInputWrongType(String testName, String text, String exception) throws Exception {
        //Then
        mockMvc.perform(delete("/pokemons/{id}", text))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString(exception)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("com.example.pokemons.testdata.pokemon.PokemonControllerTestData#invalidNumberTestData")
    public void shouldReturnBadRequestWhenTryDeleteNotExistResource(String testName, Integer id, String exception) throws Exception {
        //Then
        mockMvc.perform(delete("/pokemons/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(exception));
    }

    @Test
    public void shouldReturnBadRequestWhenPutMethodHasNoMaintainValue() throws Exception {
        //Given
        var type = Type.builder().id(1L).name("Electric").build();
        var savedType = typeRepository.save(type);
        var pokemon = Pokemon.builder()
                .id(1L)
                .name("Pikachu")
                .description("Electric mouse Pokemon")
                .power(new BigDecimal("99.00"))
                .type(savedType)
                .build();
        when(pokemonRepository.findById(1L)).thenReturn(Optional.of(pokemon));


        var updatePokemonRequest = new UpdatePokemonRequest();
        String object = objectMapper.writeValueAsString(updatePokemonRequest);

        //When
        mockMvc.perform(put("/pokemons/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(object)).andExpect(status().isBadRequest());
    }
}
