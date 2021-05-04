package com.io.petclinic.controllers;


import com.io.petclinic.controllers.entities.PetDTO;
import com.io.petclinic.model.repositories.PetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/owners/{id}")
@RestController
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @GetMapping("/pets")
    public List<PetDTO> getAllPets() {
        return (List<PetDTO>) petRepository.findAll().stream().map(pet -> new PetDTO(pet.getName(), pet.getSpecies()));
    }

//    @PostMapping("/owners/{id}/pets/")
//    Pet pet

}

