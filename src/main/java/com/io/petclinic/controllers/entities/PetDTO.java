package com.io.petclinic.controllers.entities;

public class PetDTO {
    private String name;
    private String species;
    private Long petId;

    public PetDTO() {
    }

    public PetDTO(String name, String species, Long petId) {
        this.name = name;
        this.species = species;
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }
}
