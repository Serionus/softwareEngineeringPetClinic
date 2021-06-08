package com.io.petclinic.model.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class Human {
    @Column(name = "firstname",
            columnDefinition = "TEXT")
    private String firstname;
    @Column(name = "surname",
            columnDefinition = "TEXT")
    private String surname;
    @Column(name = "login",
            columnDefinition = "TEXT")
    private String login;
    @Column(name = "password",
            columnDefinition = "TEXT")
    private String password;

    public Human(String firstname, String surname, String login, String password) {
        this.firstname = firstname;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public Human() {

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human)) return false;
        Human human = (Human) o;
        return firstname.equals(human.firstname) && surname.equals(human.surname) && login.equals(human.login) && password.equals(human.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, surname, login, password);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Human{");
        sb.append("firstname='").append(firstname).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
