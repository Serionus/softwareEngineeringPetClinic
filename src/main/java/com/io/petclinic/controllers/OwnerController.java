package com.io.petclinic.controllers;

import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.services.OwnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
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

    @DeleteMapping("owners/{id}")
    public void delete(@PathVariable Long id){
        ownerService.deleteOwner(id);
    }
}
