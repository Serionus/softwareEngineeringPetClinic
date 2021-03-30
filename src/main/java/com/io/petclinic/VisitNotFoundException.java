package com.io.petclinic;

public class VisitNotFoundException extends RuntimeException {
    VisitNotFoundException(Long id){
        super("No such visit with id = " + id);
    }
}
