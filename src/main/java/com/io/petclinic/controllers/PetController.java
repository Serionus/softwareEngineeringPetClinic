package com.io.petclinic.controllers;


import com.io.petclinic.controllers.entities.PetDTO;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.services.PetService;
import org.springframework.web.bind.annotation.GetMapping;
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

//    @PostMapping("/owners/{id}/pets/")
//    Pet pet

}

