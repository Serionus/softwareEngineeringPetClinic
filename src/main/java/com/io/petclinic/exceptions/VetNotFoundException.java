package com.io.petclinic.exceptions;

public class VetNotFoundException extends RuntimeException{
    public VetNotFoundException(Long id){
        super("No such vet with id = " + id);
    }
}
