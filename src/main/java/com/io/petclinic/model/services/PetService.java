package com.io.petclinic.model.services;

import com.io.petclinic.model.repositories.PetRepository;

public class PetService {
    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }
}
