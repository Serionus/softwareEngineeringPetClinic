package com.io.petclinic.exceptions;

public class CannotCreateVisitException extends RuntimeException {
    public CannotCreateVisitException(){
        super("There is already an existing visit at that time");
    }
}
