package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.*;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

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

    public void addVisit (Long vetId, LocalDateTime beginTime, LocalDateTime endTime){
        //visitRepository.findAllByBeginTimeAfterAndEndTimeBefore(beginTime, endTime).isEmpty()
        if((visitRepository.findAll().stream().anyMatch(visit -> beginTime.isAfter(visit.getBeginTime()) && endTime.isBefore(visit.getEndTime())))){
            throw new CannotCreateVisitException();
        } else {
            visitRepository.save(new Visit(vetRepository.findById(vetId).orElseThrow(() -> new VetNotFoundException(vetId)), beginTime, endTime));
        }
    }

    public void assignPetToVisit(Long petId, Long visitId){
        Visit wantedVisit = findVisitById(visitId);
        if(wantedVisit.getPet() == null){
            Pet wantedPet = petRepository.findById(petId).orElseThrow( () -> new PetNotFoundException(petId));
            wantedVisit.setPet(wantedPet);
            wantedPet.getVisits().add(wantedVisit);
            petRepository.save(wantedPet);
            visitRepository.save(wantedVisit);
        } else {
            throw new CannotCreateVisitException();
        }
    }

    public Visit findVisitById(Long visitId){
        return visitRepository.findById(visitId).orElseThrow(() -> new VisitNotFoundException(visitId));
    }

    public List<Visit> findAllVisits() { return visitRepository.findAll(); }

    public List<Visit> findAllVisitsByPet(Long petId) { return visitRepository.findAllByPetPetId(petId); }

    public List<Visit> findAllVisitsByVet(Long vetId) { return visitRepository.findAllByVetVetId(vetId); }

    public void changeVisitDate(LocalDateTime newBeginTime, LocalDateTime newEndTime, Long id){
        visitRepository.findById(id)
                .map(visit -> {
                    if (visitRepository.findAllByBeginTimeAfterAndEndTimeBefore(newBeginTime, newEndTime).isEmpty()) {
                        visit.setBeginTime(newBeginTime);
                        visit.setEndTime(newEndTime);
                        return visitRepository.save(visit);
                    }
                    try {
                        throw new VisitTimeConflictException(id);
                    } catch (VisitTimeConflictException e) {
                        e.printStackTrace();
                    }
                    return Optional.empty();
                })
                .orElseThrow(() ->
                        new VisitNotFoundException(id));
    }


// na pamiątkę dla Hani
//   if(visit.getBeginTime().isEqual(date)) { // lib LocalDateTime has its own isEqual method for comparing dates

    public List<Visit> getAllVetVisits(Long vetId) {
        return visitRepository.findAllByVetVetId(vetId);
    }

    public List<Visit> getAllPetVisits(Long petId) {
        return visitRepository.findAllByPetPetId(petId);
    }

    //anuluje bo pet anuluje
    public void cancelVisit(Long visitId){
        Visit cancelledVisit = findVisitById(visitId);
        cancelledVisit.setPet(null);
        visitRepository.save(cancelledVisit);
    }

    public void deleteVisit(Long visitId){
        Visit deletedVisit = findVisitById(visitId);
        deletedVisit.setPet(null);
        visitRepository.deleteById(visitId);
    }
}
