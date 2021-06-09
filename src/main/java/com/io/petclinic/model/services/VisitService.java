package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.*;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final VetRepository vetRepository;
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public VisitService(VisitRepository repository, VetRepository vetRepository, PetRepository petRepository, OwnerRepository ownerRepository) {
        this.visitRepository = repository;
        this.vetRepository = vetRepository;
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    public void addVisit (Long vetId, LocalDateTime beginTime, LocalDateTime endTime){
        //visitRepository.findAllByBeginTimeAfterAndEndTimeBefore(beginTime, endTime).isEmpty()
        if(visitRepository.findAll().stream().anyMatch(
                visit -> (
                        (beginTime.isAfter(visit.getBeginTime()) && beginTime.isBefore(visit.getEndTime()))
                        || beginTime.isEqual(visit.getBeginTime())
                        || beginTime.isEqual(visit.getEndTime())
                        || (endTime.isAfter(visit.getBeginTime()) && endTime.isBefore(visit.getEndTime()))
                        || endTime.isEqual(visit.getBeginTime())
                        || endTime.isEqual(visit.getEndTime())
                )
            )
        || beginTime.isEqual(endTime) || beginTime.isAfter(endTime) ) {
            throw new CannotCreateVisitException();
        } else {
            visitRepository.save(new Visit(vetRepository.findById(vetId).orElseThrow(() -> new VetNotFoundException(vetId)), beginTime, endTime));
        }
    }

    public void assignPetToVisit(Long petId, Long visitId){
        Visit wantedVisit = findVisitById(visitId);
        System.out.println(wantedVisit.getVisitId());
        if(wantedVisit.getPet() == null){
            Pet wantedPet = petRepository.findById(petId).orElseThrow( () -> new PetNotFoundException(petId));
            System.out.println(wantedPet.getPetId());
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

    public List<Visit> findAllVisitsByOwner(Long ownerId){
        List<Pet> pets = ownerRepository.findById(ownerId).get().getPets();
        List<Visit> visits = new ArrayList<>();
        for (Pet pet: pets) {
            visits.addAll(pet.getVisits());
        }
        return visits;
    }

    public List<Visit> findAllVisitsByVet(Long vetId) { return visitRepository.findAllByVetVetId(vetId); }

    public void changeVisitDate(LocalDateTime newBeginTime, LocalDateTime newEndTime, Long visitId){
        visitRepository.findById(visitId)
                .map(visit -> {
                    if (visitRepository.findAllByBeginTimeAfterAndEndTimeBefore(newBeginTime, newEndTime).isEmpty()) {
                        visit.setBeginTime(newBeginTime);
                        visit.setEndTime(newEndTime);
                        return visitRepository.save(visit);
                    }
                    try {
                        throw new VisitTimeConflictException(visitId);
                    } catch (VisitTimeConflictException e) {
                        e.printStackTrace();
                    }
                    return Optional.empty();
                })
                .orElseThrow(() ->
                        new VisitNotFoundException(visitId));
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
