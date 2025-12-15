package com.example.pokemons.controllers;

import com.example.pokemons.dtos.TypeDto;
import com.example.pokemons.repositories.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/types")
public class TypeController {

    private TypeRepository typeRepository;

    @GetMapping
    public List<TypeDto> getAllTypes() {

        return typeRepository.findAll().stream()
                .map(type -> new TypeDto(type.getId(), type.getName()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTypeById(@PathVariable Long id) {
        var type = typeRepository.findById(id).orElse(null);
        if (type == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new TypeDto(type.getId(), type.getName()));
    }
}
