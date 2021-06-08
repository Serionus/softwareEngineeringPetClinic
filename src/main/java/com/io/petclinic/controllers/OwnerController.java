package com.io.petclinic.controllers;

import com.io.petclinic.controllers.entities.HumanDTO;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.services.OwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class OwnerController {

    private final OwnerService ownerService;
    private final ModelMapper modelMapper;

    public OwnerController(OwnerService ownerService, ModelMapper modelMapper) {
        this.ownerService = ownerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/owners")
    public List<HumanDTO> getAllOwners(){
        return ownerService.findAllOwners().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/owners/{ownerId}")
    public HumanDTO getOwner(@PathVariable Long ownerId){
        Owner owner = ownerService.findOwner(ownerId);
        return new HumanDTO(owner.getFirstname(), owner.getSurname(), ownerId);
    }

    @PostMapping("/owners/create-owner")
    public void createOwner(@RequestParam String firstName, String surname, String login, String password){
        ownerService.createOwner(firstName, surname, login, password);
    }

    @PutMapping("/owners/{ownerId}/change-data")
    public Owner updateOwner(@RequestBody HumanDTO newOwner, @PathVariable Long ownerId){
        return ownerService.updateOwner(newOwner.getFirstname(), newOwner.getSurname(), ownerId);
    }

    @DeleteMapping("/owners/{ownerId}/delete")
    public void delete(@PathVariable Long ownerId){
        ownerService.deleteOwner(ownerId);
    }

    private HumanDTO convertToDTO(Owner owner){
        return modelMapper.map(owner, HumanDTO.class);
    }
}
