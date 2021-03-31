package com.io.petclinic.model.services;

import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.PetRepository;

import java.util.List;

public class PetService {
    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> findAllPets(){
        return repository.findAll();
    }
}
