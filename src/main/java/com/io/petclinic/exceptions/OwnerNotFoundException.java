package com.io.petclinic.exceptions;

public class OwnerNotFoundException extends RuntimeException{
    public OwnerNotFoundException(Long id){
        super("No such owner with id = " + id);
    }
}
