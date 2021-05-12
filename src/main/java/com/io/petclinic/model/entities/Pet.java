package com.io.petclinic.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table
public class Pet {

    private @Id @GeneratedValue Long petId;
    private String name;
    private String species;
    @ManyToOne
    private Owner owner;

    @OneToMany(mappedBy = "pet", fetch = FetchType.EAGER)
    private List<Visit> visits = new ArrayList<>();

    public Pet(String name, String species, Owner owner) {
        this.name = name;
        this.species = species;
        this.owner = owner;
    }

    public Pet() {

    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long id) {
        this.petId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public String getSpecies() {
        return species;
    }

    public List<Visit> getVisits() { return visits; }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return petId.equals(pet.petId) &&
                name.equals(pet.name) &&
                species.equals(pet.species) &&
                owner.equals(pet.owner) &&
                visits.equals(pet.visits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, name, species, owner, visits);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pet{");
        sb.append("petId=").append(petId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", species='").append(species).append('\'');
//        sb.append(", owner=").append(owner);
        sb.append(", visits=").append(visits);
        sb.append('}');
        return sb.toString();
    }

}
