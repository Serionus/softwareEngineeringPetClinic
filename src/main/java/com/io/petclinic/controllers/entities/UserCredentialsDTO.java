package com.io.petclinic.controllers.entities;

public class UserCredentialsDTO {
    private String login;
    private String password;

    public UserCredentialsDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserCredentialsDTO() {
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

}
