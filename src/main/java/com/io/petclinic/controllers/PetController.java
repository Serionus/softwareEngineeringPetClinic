package com.io.petclinic.controllers;

import com.io.petclinic.controllers.entities.PetDTO;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.services.OwnerService;
import com.io.petclinic.model.services.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
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
        //a po co nam modelmapper ;) azeby dzialalo
        return petService.findAllPets().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // metoda pobierająca DTO dowolnego istniejącego peta (dla admina :))
    @GetMapping("/pets/{petId}")
    public PetDTO getPet(@PathVariable Long petId){
        Pet pet = petService.findPet(petId);
        return new PetDTO(pet.getName(), pet.getSpecies(), pet.getPetId());
    }
    //prosze pamietac
    // metoda pobierająca DTO peta danego ownera
    @GetMapping("/owners/{ownerId}/pets/{petId}")
    public PetDTO getPetOfCertainOwner(@PathVariable Long ownerId, @PathVariable Long petId){
        //ojej
        Pet pet = petService.findPetByOwnerIdAndPetId(ownerId, petId);
        return new PetDTO(pet.getName(), pet.getSpecies(), pet.getPetId());
    }

    @GetMapping("/owners/{ownerId}/pets")
    public List<PetDTO> getPetOfCertainOwner(@PathVariable Long ownerId){
        //ojej
        return petService.getAllPetsOfCertainOwner(ownerId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // RequestParam适用于都参数上的
    @PostMapping("/owners/{ownerId}/pets/add")
    public void createPet(@PathVariable Long ownerId, @RequestParam String name, String species) {
        petService.createPet(ownerId, name, species);
    }

    // zgodnie z objaśnieniami Michała i Amigosa
    @PutMapping("/owners/{ownerId}/pets/{petId}/change-data")
    public Pet updatePet(@RequestBody PetDTO newPet, @PathVariable Long ownerId, @PathVariable Long petId){
        Owner newOwner = ownerService.findOwner(ownerId);
        return petService.updatePet(newPet.getName(), newPet.getSpecies(), newOwner, petId);
    }

    @DeleteMapping("/owners/{ownerId}/pets/pet{Id}/delete")
    public void delete(@PathVariable Long id){
        petService.deletePet(id);
    }

    private PetDTO convertToDTO(Pet pet){
        return modelMapper.map(pet, PetDTO.class);
    }

}

