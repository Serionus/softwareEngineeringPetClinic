package com.io.petclinic;

public class PetNotFoundException extends RuntimeException{
    PetNotFoundException(Long id){
        super("No such pet with id = " + id);
    }
}
