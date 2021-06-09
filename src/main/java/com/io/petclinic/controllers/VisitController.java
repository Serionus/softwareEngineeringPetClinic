package com.io.petclinic.controllers;

import com.io.petclinic.controllers.entities.VisitDTO;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.services.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
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

    @GetMapping("/owners/{ownerId}/pets/{petId}/visits")
    public List<VisitDTO> getPetAllVisits(@PathVariable Long ownerId, @PathVariable Long petId){
        return visitService.getAllPetVisits(petId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/vets/{vetId}/visits")
    public List<VisitDTO> getVetAllVisits(@PathVariable Long vetId){
        return visitService.getAllVetVisits(vetId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/vets/{vetId}/visits/{visitId}/delete")
    public void deleteVisitByVet(@PathVariable Long visitId){
        visitService.deleteVisit(visitId);
    }

    @PostMapping("/vets/{vetId}/visits/create-visit")
    public void createVisit(@PathVariable Long vetId, @RequestParam String beginTime, String endTime) {
        visitService.addVisit(vetId, LocalDateTime.parse(beginTime, DateTimeFormatter.ISO_DATE_TIME), LocalDateTime.parse(endTime, DateTimeFormatter.ISO_DATE_TIME));
    }
//"YY-MM-DD-THH:mm:ss:SS"
    @PutMapping("/owners/{ownerId}/pets/{petId}/visits/{visitId}/cancel")
    public void cancelVisitForPet(@PathVariable Long ownerId, @PathVariable Long petId, @PathVariable Long visitId){
        visitService.cancelVisit(visitId);
    }
    // 请您跟您的小狗取来 ♥
    @PutMapping("/owners/{ownerId}/pets/{petId}/visits/{visitId}/assign")
    public void assignPetToVisit(@PathVariable Long ownerId, @PathVariable Long petId, @PathVariable Long visitId) {
        visitService.assignPetToVisit(petId, visitId);
    }

    @PutMapping("/vets/{vetId}/visits/{visitId}/change-time")
    public void changeVisitTime(@PathVariable Long vetId, Long visitId, @RequestParam LocalDateTime newBeginTime, LocalDateTime newEndTime) {
        visitService.changeVisitDate(newBeginTime, newEndTime, visitId);
    }

    @GetMapping("/owners/{ownerId}/visits")
    public List<VisitDTO> getAllVisitsOfOwner(@PathVariable Long ownerId){
        return visitService.findAllVisitsByOwner(ownerId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private VisitDTO convertToDTO(Visit visit){
        return modelMapper.map(visit, VisitDTO.class);
    }
}
