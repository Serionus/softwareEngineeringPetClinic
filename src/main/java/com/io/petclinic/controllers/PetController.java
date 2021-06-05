package com.io.petclinic.controllers;


import com.io.petclinic.controllers.entities.HumanDTO;
import com.io.petclinic.controllers.entities.PetDTO;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.services.OwnerService;
import com.io.petclinic.model.services.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Hania todo
@RequestMapping("/owners/{id}")
@RestController
public class PetController {
    private final PetService petService;
    private final OwnerService ownerService;
    private final ModelMapper modelMapper;

    public PetController(PetService petService, OwnerService ownerService, ModelMapper modelMapper) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.modelMapper = modelMapper;
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

    @GetMapping("/owners/{ownerId}/pets/{petId}")
    public PetDTO getPet(@PathVariable Long ownerId, @PathVariable Long petId){
        Owner newOwner = ownerService.findOwner(ownerId);
        List<Pet> allOwnersPets = newOwner.getPets();
        int index = 0;
        for (Pet pet: allOwnersPets) {
            if(pet.getPetId().equals(petId)) {
                index = allOwnersPets.indexOf(pet);
            }
        }
       return new PetDTO(allOwnersPets.get(index).getName(), allOwnersPets.get(index).getSpecies());
    }

    @PostMapping("/owners/{ownerId}/pets/add")
    public void createPet(@PathVariable Long ownerId, @RequestParam String name, String species) {
        petService.createPet(ownerId, name, species);
    }

    // zgodnie z objaśnieniami Michała i Amigosa
    @PutMapping("/owners/{id}/pets/{petId}/change-data")
    public Pet updatePet(@RequestBody PetDTO newPet, @PathVariable Long ownerId, @PathVariable Long petId){
        Owner newOwner = ownerService.findOwner(ownerId);
        return petService.updatePet(newPet.getName(), newPet.getSpecies(), newOwner, petId);
    }

    @DeleteMapping("/owners/{id}/pets/pet{Id}/delete")
    public void delete(@PathVariable Long id){
        petService.deletePet(id);
    }

    private PetDTO convertToDTO(Pet pet){
        return modelMapper.map(pet, PetDTO.class);
    }

}

