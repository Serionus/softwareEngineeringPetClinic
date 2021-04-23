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
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final VisitRepository visitRepository;
    private final PetService petService;
    private final VisitService visitService;

    public OwnerService(OwnerRepository ownerRepository, PetRepository petRepository, PetService petService, VisitRepository visitRepository, VisitService visitService) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.visitRepository = visitRepository;
        this.petService = petService;
        this.visitService = visitService;
    }

    public List<Owner> findAllOwners(){
        return ownerRepository.findAll();
    }

    public void createOwner(String firstname, String surname){
        Owner owner = new Owner(firstname, surname);
        ownerRepository.save(owner);
    }

    public Owner findOwner(Long id){
        return ownerRepository.findById(id)
                .orElseThrow( () -> new OwnerNotFoundException(id));
    }

    public Owner updateOwner(String newFirstName, String newSurname, Long id){
        Owner updatedOwner = new Owner(newFirstName, newSurname);
        return ownerRepository.findById(id)
                .map( owner -> {
                    owner.setFirstname(updatedOwner.getFirstname());
                    owner.setSurname(updatedOwner.getSurname());
                    return ownerRepository.save(owner);
                }).orElseGet( () -> {
                    updatedOwner.setOwnerId(id);
                    return ownerRepository.save(updatedOwner);
                });
    }

    // owners should stay in database even if irl they no longer attend the clinic
    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
        //hmm ciekawe czemu
    }


    public Pet getOwnersPet(Long ownerId, Long petId){
        Owner wantedOwner = ownerRepository.findById(ownerId)
                .orElseThrow( () -> new OwnerNotFoundException(ownerId));
        System.out.println(wantedOwner.getPets().size());
        return wantedOwner.getPetById(petId);
    }

    public List<Pet> getAllPets(Long ownerId){
        Owner wantedOwner = ownerRepository.findById(ownerId)
                .orElseThrow( () -> new OwnerNotFoundException(ownerId));
        return wantedOwner.getPets();
    }

    public void addPet(Long id, String name, String species){
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new OwnerNotFoundException(id));
        Pet newPet = petService.createPet(name, species);
        System.out.println(owner.toString());
        owner.addNewPet(newPet);
        petRepository.save(newPet);
        ownerRepository.save(owner);
    }

    public void deleteVisit(Long ownerId, Long visitId, Long petId){
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException(ownerId));
        Pet pet = owner.getPetById(petId);
        Visit wantedVisit = pet.getVisitById(visitId);
        owner.deleteVisit(pet, wantedVisit);
        visitRepository.save(wantedVisit);
        petRepository.save(pet);
    }
    // dziękuję :) ;)

    public void addVisit (Long ownerId, Long petId, int year, int month, int day, int hour, int minutes){
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException(ownerId));
        Pet pet = owner.getPetById(petId);
        if (pet == null){
            throw new PetNotFoundException(petId);
        }

        LocalDateTime wantedDate = LocalDateTime.of(year,month,day,hour,minutes);
        Visit wantedVisit = visitService.findVisitByDate(wantedDate);
        if(wantedVisit == null) {
            System.out.println("Nie ma takiej wizyty chopie");
        }

        List<Pet> allPets = petRepository.findAll();
        for (Pet p: allPets) {
            if (p.getVisitById(wantedVisit.getVisitId()) != null) {
                System.out.println(pet.getVisits());
                System.out.println("Termin wizyty zajęty! Wybierz inny - add visit :)");
                return;
            }
        }

        owner.addNewVisit(pet, wantedVisit);
        petRepository.save(pet);
    }

    public void rescheduleVisit(Long ownerId, Long visitId, Long petId, int year, int month, int day, int hour, int minutes) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow( () -> new OwnerNotFoundException(ownerId));
        Pet pet = owner.getPetById(petId);

        if (pet == null) {
            throw new PetNotFoundException(petId);
        }

        Visit visitToReschedule = pet.getVisitById(visitId);
        if (visitToReschedule == null) {
            throw new VisitNotFoundException(visitId);
        }

        List<Pet> allPets = petRepository.findAll();
        LocalDateTime rescheduledTime = LocalDateTime.of(year, month, day, hour, minutes);
        for (Pet p: allPets) {
            List<Visit> allPetsVisits = p.getVisits();

            for (Visit v: allPetsVisits) {
                if (v.getBeginTime().isEqual(rescheduledTime)) {
                    System.out.println("Termin wizyty zajęty! Wybierz inny - reschedule :)");
                    return;
                }
            }
        }
        System.out.println("to się nie wykona 2: electric bogaloo");
        deleteVisit(ownerId, visitId, petId);
        addVisit(ownerId, petId, year, month, day, hour, minutes);
    }


}
