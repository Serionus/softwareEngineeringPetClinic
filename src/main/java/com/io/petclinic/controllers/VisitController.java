package com.io.petclinic.controllers;

import com.io.petclinic.controllers.entities.VisitDTO;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.services.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VisitController {
    private final VisitService visitService;
    private final ModelMapper modelMapper;

    public VisitController(VisitService visitService, ModelMapper modelMapper) {
        this.visitService = visitService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/visits")
    public List<VisitDTO> getAllVisits(){
        return visitService.findAllVisits().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}/visits}")
    public List<VisitDTO> getPetAllVisits(@PathVariable Long ownerId, Long petId){
        return visitService.getAllPetVisits(petId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private VisitDTO convertToDTO(Visit visit){
        return modelMapper.map(visit, VisitDTO.class);
    }
}
