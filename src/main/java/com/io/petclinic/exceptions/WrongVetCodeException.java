package com.io.petclinic.exceptions;

public class WrongVetCodeException extends RuntimeException{
    public WrongVetCodeException() {
        super("Given vetcode is wrong");
    }
}
