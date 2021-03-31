package com.io.petclinic.exceptions;

public class PetNotFoundException extends RuntimeException{
    public PetNotFoundException(Long id){
        super("No such pet with id = " + id);
    }
}
