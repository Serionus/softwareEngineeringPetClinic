package com.io.petclinic.exceptions;

public class VisitNotFoundException extends RuntimeException {
    public VisitNotFoundException(Long id){
        super("No such visit with id = " + id);
    }
}
