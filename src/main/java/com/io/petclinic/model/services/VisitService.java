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

    public Visit createVisit(LocalDateTime visitDate) {
        Visit visit = new Visit(1000, 10, 2, 1, 10);
        visitRepository.save(visit);
        return visit;
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

    // czy potrzebujemy tego? czy lepiej jedna metoda na zasadzie userId
    // zamiast X metod do petId, ownerId i vetId
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
