package com.io.petclinic.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Pet {

    private @Id @GeneratedValue Long petId;
    private String name;
    private String species;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Visit> visits = new ArrayList<>();

    public Pet(String name, String species) {
        this.name = name;
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

    public Visit getVisitById(Long id){
        for (Visit visit: visits){
            System.out.println("szuszu współpracuj ze mną proszę");
            if (visit.getVisitId().equals(id)){
                return visit;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return petId.equals(pet.petId) && name.equals(pet.name)
                && species.equals(pet.species);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, name, species);
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
