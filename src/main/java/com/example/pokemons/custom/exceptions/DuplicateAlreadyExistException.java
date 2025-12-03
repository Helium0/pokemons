package com.example.pokemons.custom.exceptions;

public class DuplicateAlreadyExistException extends RuntimeException {

    public DuplicateAlreadyExistException(String message) {
        super(message);
    }
}
