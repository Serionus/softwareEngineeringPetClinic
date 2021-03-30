package com.io.petclinic;

public class VetNotFoundException extends RuntimeException{
    VetNotFoundException(Long id){
        super("No such vet with id = " + id);
    }
}
