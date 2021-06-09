package com.io.petclinic.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
        super("There is already existing user with such login!");
    }
}
