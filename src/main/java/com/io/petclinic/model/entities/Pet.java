package com.io.petclinic.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Pet {

    private @Id @GeneratedValue Long petId;
    private Long ownerId;
    private String species;
    @OneToMany
    private List<Visit> visits;

    public Pet(String species) {
        this.species = species;
    }

    public Pet() {

    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long id) {
        this.petId = id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return petId.equals(pet.petId) &&
                species.equals(pet.species);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, species);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pet{");
        sb.append("id=").append(petId);
        sb.append(", species='").append(species).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
