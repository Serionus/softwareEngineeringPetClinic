package com.io.petclinic.controllers;


import com.io.petclinic.controllers.entities.HumanDTO;
import com.io.petclinic.controllers.entities.PetDTO;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.services.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/owners/{id}")
@RestController
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

//    @GetMapping("/pets")
//    public List<PetDTO> getAllPets() {
//        return (List<PetDTO>) petService.findAllPets().stream().map(pet -> new PetDTO(pet.getName(), pet.getSpecies()));
//    }

    @GetMapping("/pets/{id}")
    public PetDTO getPet(@PathVariable Long id){
        Pet pet = petService.findPet(id);
        return new PetDTO(pet.getName(), pet.getSpecies());
    }

    //    @GetMapping("/owners/{ownerId}/pets/{petId}")
//    public Pet getPet(@PathVariable Long ownerId, Long petId){
////       return ownerService.getOwnersPet(ownerId, petId);
//    }

    @PostMapping("/owners/{ownerId}/pets/add")
    public void addPet(@PathVariable Long ownerId, @RequestParam String name, String species){
//        Pet newPet = petService.createPet(name, species);
//        ownerService.createPet(ownerId, newPet);
    }

}

