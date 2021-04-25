package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.exceptions.PetNotFoundException;
import com.io.petclinic.exceptions.VetNotFoundException;
import com.io.petclinic.exceptions.VisitNotFoundException;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final VetRepository vetRepository;
    private final PetRepository petRepository;

    public VisitService(VisitRepository repository, VetRepository vetRepository, PetRepository petRepository) {
        this.visitRepository = repository;
        this.vetRepository = vetRepository;
        this.petRepository = petRepository;
    }

    public Visit findVisitById(Long visitId){
        return visitRepository.findById(visitId).orElseThrow(() -> new VisitNotFoundException(visitId));
    }

    public List<Visit> findAllVisits() { return visitRepository.findAll(); }

    public void deleteVisitById(Long id) {
        visitRepository.deleteById(id);
    }

    public Visit updateVisit(Visit newVisit, Long id){
        return visitRepository.findById(id)
                .map( visit -> {
                    visit.setBeginTime(newVisit.getBeginTime());
                    visit.setEndTime(newVisit.getEndTime());
                    visit.setVet(newVisit.getVet());
                    visit.setPet(newVisit.getPet());
                    return visitRepository.save(visit);
                }).orElseGet( () -> {
                    newVisit.setVisitId(id);
                    return visitRepository.save(newVisit);
                });
    }

//    public Visit findVisitByDate(LocalDateTime date){
//        List<Visit> allVisits = visitRepository.findAll();
//        for (Visit visit: allVisits) {
//            if(visit.getBeginTime().isEqual(date)) { // lib LocalDateTime has its own isEqual method for comparing dates
//                return visit;
//            }
//        }
//        return null;
//    }

    public List<Visit> getAllVetVisits(Long vetId) {
        Vet wantedVet = vetRepository.findById(vetId)
                .orElseThrow( () -> new VetNotFoundException(vetId));
        return wantedVet.getVisits();
    }

    public List<Visit> getAllPetVisits(Long petId) {
        return visitRepository.findAllByPetPetId(petId);
    }

    public void cancelVisit(Long visitId){
        Visit cancelledVisit = findVisitById(visitId);
        cancelledVisit.setPet(null);
        visitRepository.save(cancelledVisit);
    }

//    publiv
}
