package com.example.pokemons.integration.service;

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

    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "com.example.pokemons.testdata.type.TypeServiceIntegrationTestData#validTypeValues")
    public void providedValidUniqueTypeNameShouldCreateRecordInDatabase(String testName, String name) {
        //When
        var createdType = typeService.findOrCreateType(name);
        Optional<Type> foundedType = typeRepository.findByName(createdType.getName());

        //Then
        assertEquals(name, createdType.getName());
        assertEquals(name, foundedType.get().getName());
        assertTrue(foundedType.isPresent());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "com.example.pokemons.testdata.type.TypeServiceIntegrationTestData#validTypeValues")
    public void findOrCreateTypeWhenAlreadyExistShouldReturnTheSameType(String testName, String name) {
        //Given
        var savedType = typeRepository.save(Type.builder().name(name).build());

        //When
        Type firstType = typeService.findOrCreateType(savedType.getName());
        Type anotherType = typeService.findOrCreateType(savedType.getName());

        //Then
        assertEquals(savedType.getId(), firstType.getId());
        assertEquals(savedType.getId(), anotherType.getId());
        assertEquals(firstType.getId(), anotherType.getId());
    }

    @Test
    public void invalidNullTypeNameShouldThrowException() {
        //Given
        var customType = Type.builder().build();

        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> typeService.findOrCreateType(customType.getName()));

        //Then
        assertEquals("Type name should not be null or blank", exception.getMessage());
    }

    @Test
    public void providedTooLongTypeNameShouldThrowException() {
        //Given
        var customType = Type.builder().name("A" + "c".repeat(101)).build();

        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> typeService.findOrCreateType(customType.getName()));

        //Then
        assertEquals("Type name length should not be greater than 100 signs", exception.getMessage());
    }
}
