package com.io.petclinic.controllers.entities;

public class HumanDTO {
    private String firstname;
    private String surname;
    private Long humanId;
    private String login;
    private String password;
    private String vetCode;

    public HumanDTO() {
    }

    public HumanDTO(String firstname, String surname, Long humanId, String login, String password) {
        this.firstname = firstname;
        this.surname = surname;
        this.humanId = humanId;
        this.login = login;
        this.login = password;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVetCode() {
        return vetCode;
    }

    public void setVetCode(String vetCode) {
        this.vetCode = vetCode;
    }
}
