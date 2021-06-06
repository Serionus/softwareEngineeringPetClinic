package com.io.petclinic.exceptions;

public class OwnersPetNotFoundException extends RuntimeException {
    public OwnersPetNotFoundException(Long ownerId, Long petId) {
        super("No such pet with id = " + petId + " belonging to owner with id = " + ownerId);
    }
}
