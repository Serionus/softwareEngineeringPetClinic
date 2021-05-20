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
import java.util.stream.Collectors;


@RestController
public class OwnerController {

    private final OwnerService ownerService;
    private final PetService petService;
    private final ModelMapper modelMapper;

    public OwnerController(OwnerService ownerService, PetService petService, ModelMapper modelMapper) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/owners")
    public List<HumanDTO> getAllOwners(){
        return ownerService.findAllOwners().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/owners/{id}")
    public HumanDTO getOwner(@PathVariable Long id){
        Owner owner = ownerService.findOwner(id);
        return new HumanDTO(owner.getFirstname(), owner.getSurname());
    }

//    @PutMapping("/owners/{id}")
//    public Owner updateOwner(@RequestBody Owner newOwner, @PathVariable Long id){
////        return ownerService.updateOwner(newOwner, id);
//    }

    @DeleteMapping("/owners/{id}")
    public void delete(@PathVariable Long id){
        ownerService.deleteOwner(id);
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

    private HumanDTO convertToDTO(Owner owner){
        return modelMapper.map(owner, HumanDTO.class);
    }
}
