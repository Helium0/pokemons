package com.example.pokemons.services;

import com.example.pokemons.custom.exceptions.DuplicateAlreadyExistException;
import com.example.pokemons.entities.Type;
import com.example.pokemons.helper.ValidatorHelper;
import com.example.pokemons.repositories.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeService {

    private TypeRepository typeRepository;

    public Type findOrCreateType(String name) {
            var validatedType = ValidatorHelper.validateAndTrimText(name, "Type name");

            Optional<Type> foundType = typeRepository.findByName(validatedType);
            if (foundType.isPresent()) {
                return foundType.get();
            }

            var type = new Type();
            type.setTypeName(validatedType);
            return typeRepository.save(type);
    }
}
