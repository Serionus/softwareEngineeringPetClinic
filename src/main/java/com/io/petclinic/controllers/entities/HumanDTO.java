package com.io.petclinic.controllers.entities;

public class HumanDTO {
    private String firstname;
    private String surname;

    public HumanDTO() {
    }

    public HumanDTO(String firstname, String surname) {
        this.firstname = firstname;
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
