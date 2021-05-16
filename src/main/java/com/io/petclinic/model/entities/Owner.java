package com.io.petclinic.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Owner")
@Table(name = "owner",
        uniqueConstraints = {@UniqueConstraint(name = "unique_name", columnNames = {"firstname", "surname"})})
public class Owner extends Human {
    @Id
    @SequenceGenerator(
            name = "id_sequence",
            sequenceName = "id_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "id_sequence")
    @Column(name = "owner_id",
            updatable = false)
    private Long ownerId;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

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

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;
        if (!super.equals(o)) return false;
        Owner owner = (Owner) o;
        return ownerId.equals(owner.ownerId) && pets.equals(owner.pets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ownerId, pets);
    }

    @Override
    public String toString() {
        final StringBuilder message = new StringBuilder("Owner{");
        message.append("firstname='").append(getFirstname()).append("', surname='").append(getSurname()).append("', id=").append(ownerId).append("}");
        return message.toString();
    }

}
