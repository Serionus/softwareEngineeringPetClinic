package com.io.petclinic.controllers;

import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.services.VetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping("/vets")
    public List<Vet> getAllVets(){
        return vetService.findAllVets();
    }

    @GetMapping("/vets/{id}")
    public Vet getVet(@PathVariable Long id){
        return vetService.findVet(id);
    }

    @PutMapping("/vets/{id}")
    public Vet updateVet(@RequestBody Vet newVet, @PathVariable Long id){
        return vetService.updateVet(newVet, id);
    }


    @DeleteMapping("vets/{id}")
    public void deleteVet(@PathVariable Long id){
        vetService.deleteVet(id);
    }
}
