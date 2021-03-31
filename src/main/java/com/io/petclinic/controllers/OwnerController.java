package com.io.petclinic.controllers;

import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.services.OwnerService;
import org.springframework.web.bind.annotation.*;


@RestController
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners")
    public void getAllOwners(){
        ownerService.findAllOwners();
    }

    @GetMapping("/owners/{id}")
    public void getOwner(@PathVariable Long id){
        ownerService.findOwner(id);
    }

    @PutMapping("/owners/{id}")
    public void updateOwner(@RequestBody Owner newOwner, @PathVariable Long id){
        ownerService.updateOwner(newOwner, id);
    }

    @DeleteMapping("owners/{id}")
    public void delete(@PathVariable Long id){
        ownerService.deleteOwner(id);
    }
}
