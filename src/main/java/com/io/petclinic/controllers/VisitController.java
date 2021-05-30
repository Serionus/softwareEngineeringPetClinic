package com.io.petclinic.controllers;

import com.io.petclinic.controllers.entities.VisitDTO;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.services.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitController {
    private final VisitService visitService;
    private final ModelMapper modelMapper;

    public VisitController(VisitService visitService, ModelMapper modelMapper) {
        this.visitService = visitService;
        this.modelMapper = modelMapper;
    }

    private VisitDTO convertToDTO(Visit visit){
        return modelMapper.map(visit, VisitDTO.class);
    }
}
