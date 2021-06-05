package com.io.petclinic.exceptions;

public class VisitTimeConflictException extends Exception {
    public VisitTimeConflictException(Long id){
        super("Cannot reschedule visit with id = " + id);
    }
}
