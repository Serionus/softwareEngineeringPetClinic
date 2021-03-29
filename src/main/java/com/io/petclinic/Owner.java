package com.io.petclinic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Owner extends Human {

    private @Id @GeneratedValue Long id;

    public Owner() {
        super();
    }

    public Owner(String firstname, String surname) {
        super(firstname, surname);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;
        if (!super.equals(o)) return false;
        Owner owner = (Owner) o;
        return id.equals(owner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        final StringBuilder message = new StringBuilder("Owner{");
        message.append("firstname='").append(getFirstname()).append("', surname='").append(getSurname()).append("', id=").append(id).append("}");
        return message.toString();
    }
}
