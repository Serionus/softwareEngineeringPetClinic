package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.exceptions.PetNotFoundException;
import com.io.petclinic.exceptions.VetNotFoundException;
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

@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final PetRepository petRepository;

    public VisitService(VisitRepository repository, OwnerRepository ownerRepository, VetRepository vetRepository, PetRepository petRepository) {
        this.visitRepository = repository;
        this.ownerRepository = ownerRepository;
        this.vetRepository = vetRepository;
        this.petRepository = petRepository;
    }

    public List<Visit> findAllVisits() { return visitRepository.findAll(); }

    public Visit createVisit(int year, int month, int day, int hour, int minute) {
        Visit visit = new Visit(year, month, day, hour, minute);
        visitRepository.save(visit);
        return visit;
    }

    public void generateVisits() {
        for(int i = 1; i < 6; i++) {
            for (int j = 0; j < 16; j++) {
                int hour = j/2;
                if (j%2 == 0){
                    createVisit(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), i, 9 + hour, 0);
                } else {
                    createVisit(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), i, 9 + hour, 30);
                }
            }
        }
    }

    public Visit updateVisit(Visit newVisit, Long id){
        return visitRepository.findById(id)
                .map( visit -> {
                    visit.setBeginTime(newVisit.getBeginTime());
                    return visitRepository.save(visit);
                }).orElseGet( () -> {
                    newVisit.setVisitId(id);
                    return visitRepository.save(newVisit);
                });
    }

    public Visit findVisitByDate(LocalDateTime date){
        List<Visit> allVisits = visitRepository.findAll();
        for (Visit visit: allVisits) {
            if(visit.getBeginTime().isEqual(date)) { // lib LocalDateTime has its own isEqual method for comparing dates
                return visit;
            }
        }
        return null;
    }

    public void deleteVisitById(Long id) {
        visitRepository.deleteById(id);
    }

    public List<Visit> getAllOwnerVisits(Long ownerId) {
        Owner wantedOwner = ownerRepository.findById(ownerId)
                .orElseThrow( () -> new OwnerNotFoundException(ownerId));
        List<Pet> pets = wantedOwner.getPets();
        for (Pet pet: pets) {
            return pet.getVisits();
        }
        return null;
    }

    public List<Visit> getAllVetVisits(Long vetId) {
        Vet wantedVet = vetRepository.findById(vetId)
                .orElseThrow( () -> new VetNotFoundException(vetId));
        return wantedVet.getVisits();
    }

    public List<Visit> getAllPetVisits(Long petId) {
        Pet wantedPet = petRepository.findById(petId)
                .orElseThrow( () -> new PetNotFoundException(petId));
        return wantedPet.getVisits();
    }

}
