package com.io.petclinic.controllers;

import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.services.OwnerService;
import com.io.petclinic.model.services.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OwnerController {

    private final OwnerService ownerService;
    private final PetService petService;

    public OwnerController(OwnerService ownerService, PetService petService) {
        this.ownerService = ownerService;
        this.petService = petService;
    }

    @GetMapping("/owners")
    public List<Owner> getAllOwners(){
        return ownerService.findAllOwners();
    }

    @GetMapping("/owners/{id}")
    public Owner getOwner(@PathVariable Long id){
        return ownerService.findOwner(id);
    }

    @PutMapping("/owners/{id}")
    public Owner updateOwner(@RequestBody Owner newOwner, @PathVariable Long id){
        return ownerService.updateOwner(newOwner, id);
    }

    @DeleteMapping("/owners/{id}")
    public void delete(@PathVariable Long id){
        ownerService.deleteOwner(id);
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}")
    public Pet getPet(@PathVariable Long ownerId, Long petId){
       return ownerService.getOwnersPet(ownerId, petId);
    }

    @PostMapping("/owners/{ownerId}/pets/add")
    public void addPet(@PathVariable Long ownerId, @RequestParam String name, String species){
        Pet newPet = petService.createPet(name, species);
        ownerService.addPet(ownerId, newPet);
    }
}
