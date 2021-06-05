package com.io.petclinic.controllers;

import com.io.petclinic.controllers.entities.HumanDTO;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.services.VetService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VetController {
    private final VetService vetService;
    private final ModelMapper modelMapper;

    public VetController(VetService vetService, ModelMapper modelMapper) {
        this.vetService = vetService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/vets")
    public List<HumanDTO> getAllVets(){
        return vetService.findAllVets().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/vets/{id}")
    public HumanDTO getVet(@PathVariable Long id){
        Vet vet = vetService.findVet(id);
        return new HumanDTO(vet.getFirstname(), vet.getSurname());
    }

    @PostMapping("/vets/create-vet")
    public void createVet(@RequestParam String firstName, String surname){
        vetService.createVet(firstName, surname);
    }

    @PutMapping("/vets/{id}/change-data")
    public Vet updateVet(@RequestBody HumanDTO newVet, @PathVariable Long id){
        return vetService.updateVet(newVet.getFirstname(), newVet.getSurname(), id);
    }

    @DeleteMapping("vets/{id}/delete")
    public void deleteVet(@PathVariable Long id){
        vetService.deleteVet(id);
    }

    private HumanDTO convertToDTO(Vet vet){
        return modelMapper.map(vet, HumanDTO.class);
    }
}
