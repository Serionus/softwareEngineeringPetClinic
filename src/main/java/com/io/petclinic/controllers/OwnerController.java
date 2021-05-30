package com.io.petclinic.controllers;

import com.io.petclinic.controllers.entities.HumanDTO;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.services.OwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


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

    @GetMapping("/owners/{id}")
    public HumanDTO getOwner(@PathVariable Long id){
        Owner owner = ownerService.findOwner(id);
        return new HumanDTO(owner.getFirstname(), owner.getSurname());
    }

    @PostMapping("/owners/create-owner")
    public void createOwner(@RequestParam String firstName, String surname){
        ownerService.createOwner(firstName, surname);
    }

    @PutMapping("/owners/{id}/change-data")
    public Owner updateOwner(@RequestBody HumanDTO newOwner, @PathVariable Long id){
        return ownerService.updateOwner(newOwner.getFirstname(), newOwner.getSurname(), id);
    }

    @DeleteMapping("/owners/{id}/delete")
    public void delete(@PathVariable Long id){
        ownerService.deleteOwner(id);
    }

    private HumanDTO convertToDTO(Owner owner){
        return modelMapper.map(owner, HumanDTO.class);
    }
}
