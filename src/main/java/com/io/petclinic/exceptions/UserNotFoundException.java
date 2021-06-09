package com.io.petclinic.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("No such user with given credentials");
    }

}
