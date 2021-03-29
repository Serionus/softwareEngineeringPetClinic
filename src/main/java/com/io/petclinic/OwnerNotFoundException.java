package com.io.petclinic;

public class OwnerNotFoundException extends RuntimeException{
    OwnerNotFoundException(Long id){
        super("No such owner with id = " + id);
    }
}
