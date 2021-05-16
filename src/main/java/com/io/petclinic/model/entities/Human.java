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

    public Human(String firstname, String surname) {
        this.firstname = firstname;
        this.surname = surname;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human)) return false;
        Human human = (Human) o;
        return firstname.equals(human.firstname) && surname.equals(human.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, surname);
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
