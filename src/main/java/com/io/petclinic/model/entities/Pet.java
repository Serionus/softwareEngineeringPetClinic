package com.io.petclinic.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Pet")
@Table(name = "pet")
public class Pet {

    @Id
    @SequenceGenerator(
            name = "id_sequence",
            sequenceName = "id_sequence",
            allocationSize = 1)
    @GeneratedValue
            (strategy = GenerationType.SEQUENCE,
            generator = "id_sequence")
    @Column(name = "pet_id",
            updatable = false)
    private Long petId;

    @Column(name = "name",
            columnDefinition = "TEXT")
    private String name;

    @Column(name = "species",
            columnDefinition = "TEXT")
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
        sb.append(", owner=").append(owner);
        sb.append(", visits=").append(visits);
        sb.append('}');
        return sb.toString();
    }

}
