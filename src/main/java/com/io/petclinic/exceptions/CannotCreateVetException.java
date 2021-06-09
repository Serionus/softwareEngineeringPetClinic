package com.io.petclinic.exceptions;

public class CannotCreateVetException extends RuntimeException {
    public CannotCreateVetException() {
        super("Something went wrong with creating vet!");
    }
}
