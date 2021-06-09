package com.io.petclinic.exceptions;

public class CannotCreateOwnerException extends RuntimeException {
    public CannotCreateOwnerException() {
        super("Something went wrong with creating owner!");
    }
}
