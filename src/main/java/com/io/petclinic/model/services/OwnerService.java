package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.exceptions.PetNotFoundException;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final VisitRepository visitRepository;
    private final PetService petService;

    public OwnerService(OwnerRepository ownerRepository, PetRepository petRepository, PetService petService, VisitRepository visitRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.visitRepository = visitRepository;
        this.petService = petService;
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

}
