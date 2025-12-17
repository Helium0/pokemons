package com.example.pokemons.controllers;

import com.example.pokemons.dtos.TrainerDto;
import com.example.pokemons.repositories.TrainerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/trainers")
@AllArgsConstructor
@Getter
public class TrainerController {

    private TrainerRepository trainerRepository;

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getTrainer(@PathVariable Long id) {
        var trainer = trainerRepository.findById(id).orElse(null);
        if (trainer == null) {
            return ResponseEntity.notFound().build();
        }
        var trainerDto = new TrainerDto(trainer.getId(), trainer.getName(), trainer.getSurname());
        return ResponseEntity.ok(trainerDto);
    }

    @GetMapping()
    public List<TrainerDto> getTrainers(@RequestParam(required = false, defaultValue = "", name = "sort") String sortBy) {
       if (!Set.of("name", "email").contains(sortBy))
           sortBy = "name";

        return trainerRepository.findAll()
                .stream()
                .map(trainer -> new TrainerDto(trainer.getId(), trainer.getName(), trainer.getSurname()))
                .toList();
    }
}
