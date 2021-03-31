package com.io.petclinic.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Owner extends Human {

    private @Id @GeneratedValue Long ownerId;

    public Owner() {
        super();
    }

    public Owner(String firstname, String surname) {
        super(firstname, surname);
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long id) {
        this.ownerId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;
        if (!super.equals(o)) return false;
        Owner owner = (Owner) o;
        return ownerId.equals(owner.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ownerId);
    }

    @Override
    public String toString() {
        final StringBuilder message = new StringBuilder("Owner{");
        message.append("firstname='").append(getFirstname()).append("', surname='").append(getSurname()).append("', id=").append(ownerId).append("}");
        return message.toString();
    }
}
