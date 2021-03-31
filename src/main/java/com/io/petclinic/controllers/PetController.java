package com.io.petclinic.controllers;


import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.PetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/owners/{id}")
@RestController
public class PetController {

    private final PetRepository repository;

    public PetController(PetRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/pets")
    public List<Pet> getAllPets() {
        return repository.findAll();
    }

//    @PostMapping("/owners/{id}/pets/")
//    Pet pet

}

