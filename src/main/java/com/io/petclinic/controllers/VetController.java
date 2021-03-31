package com.io.petclinic.controllers;

import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.services.VetService;
import org.springframework.web.bind.annotation.*;

@RestController
public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping("/vets")
    public void getAllVets(){
        vetService.findAllVets();
    }

    @GetMapping("/vets/{id}")
    public void getVetByID(@PathVariable Long id){
        vetService.findVet(id);
    }

    @PutMapping("/vets/{id}")
    public void updatedVet(@RequestBody Vet newVet, @PathVariable Long id){
        vetService.updateVet(newVet, id);
    }


    @DeleteMapping("vets/{id}")
    public void deleteVet(@PathVariable Long id){
        vetService.deleteVet(id);
    }
}
