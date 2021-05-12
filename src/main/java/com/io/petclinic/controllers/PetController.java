package com.io.petclinic.controllers;


import com.io.petclinic.controllers.entities.HumanDTO;
import com.io.petclinic.controllers.entities.PetDTO;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.services.PetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/owners/{id}")
@RestController
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/pets")
    public List<PetDTO> getAllPets() {
        return (List<PetDTO>) petService.findAllPets().stream().map(pet -> new PetDTO(pet.getName(), pet.getSpecies()));
    }

    @GetMapping("/pets/{id}")
    public PetDTO getPet(@PathVariable Long id){
        Pet pet = petService.findPet(id);
        return new PetDTO(pet.getName(), pet.getSpecies());
    }

}

