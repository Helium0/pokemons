package com.example.pokemons.integration.service;

import com.example.pokemons.custom.exceptions.DuplicateAlreadyExistException;
import com.example.pokemons.entities.Type;
import com.example.pokemons.repositories.TypeRepository;
import com.example.pokemons.services.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TypeServiceIntegrationTest {

    @Autowired
    private TypeService typeService;
    @Autowired
    private TypeRepository typeRepository;

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#validStringValues")
    public void providedValidUniqueTypeNameShouldCreateRecordInDatabase(String name) {
        //When
        var createdType = typeService.createType(name);
        Optional<Type> foundedType = typeRepository.findByName(createdType.getName());

        //Then
        assertEquals(name, createdType.getName());
        assertEquals(name, foundedType.get().getName());
        assertTrue(foundedType.isPresent());
    }

    @ParameterizedTest
    @MethodSource(value = "com.example.pokemons.testdata.common.CommonStringTestData#validStringValues")
    public void duplicatedTypeNameShouldThrowException(String name) {
        //Given
        typeService.createType(name);
        Optional<Type> foundedType = typeRepository.findByName(name);

        //When
        DuplicateAlreadyExistException exception = assertThrows(DuplicateAlreadyExistException.class,
                () -> typeService.createType(name));

        //Then
        assertEquals("Type: " + name + " already exists in DB on ID: " + foundedType.get().getId(), exception.getMessage());
    }

    @Test
    public void invalidNullTypeNameShouldThrowException() {
        //Given
        var customType = Type.builder().build();

        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> typeService.createType(customType.getName()));

        //Then
        assertEquals("Type name should not be null or blank", exception.getMessage());
    }

    @Test
    public void providedTooLongTypeNameShouldThrowException() {
        //Given
        var customType = Type.builder().name("A" + "c".repeat(101)).build();

        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> typeService.createType(customType.getName()));

        //Then
        assertEquals("Type name length should not be greater than 100 signs", exception.getMessage());
    }
}
