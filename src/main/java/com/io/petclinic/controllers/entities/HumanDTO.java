package com.io.petclinic.controllers.entities;

public class HumanDTO {
    private String firstname;
    private String surname;
    private Long humanId;

    public HumanDTO() {
    }

    public HumanDTO(String firstname, String surname, Long humanId) {
        this.firstname = firstname;
        this.surname = surname;
        this.humanId = humanId;
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

    public Long getHumanId() {
        return humanId;
    }

    public void setHumanId(Long humanId) {
        this.humanId = humanId;
    }
}
