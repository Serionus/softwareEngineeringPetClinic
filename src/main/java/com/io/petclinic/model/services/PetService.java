package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.exceptions.PetNotFoundException;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final VisitRepository visitRepository;
    private final OwnerRepository ownerRepository;

    public PetService(PetRepository repository, VisitRepository visitRepository, OwnerRepository ownerRepository) {
        this.petRepository = repository;
        this.visitRepository = visitRepository;
        this.ownerRepository = ownerRepository;
    }

    public Pet findPet(Long id){
        return petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    }

    public List<Pet> findAllPets(){
        return petRepository.findAll();
    }

    public Pet updatePet(String newName, String newSpecies, Owner newOwner, Long petId){
        Pet updatedPet = new Pet(newName, newSpecies, newOwner);
        return petRepository.findById(petId)
                .map( pet -> {
                    pet.setName(newName);
                    pet.setSpecies(newSpecies);
                    pet.setOwner(newOwner);
                    return petRepository.save(pet);
                }).orElseGet( () -> {
                    updatedPet.setPetId(petId);
                    return petRepository.save(updatedPet);
                });
    }

    public void addPet(Long ownerId, String name, String species){
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException(ownerId));
        Pet newPet = new Pet(name, species, owner);
        owner.getPets().add(newPet);
        petRepository.save(newPet);
        ownerRepository.save(owner);
    }

    public List<Pet> getAllPets(Long ownerId){
        return ownerRepository.findById(ownerId).map(Owner::getPets).orElseThrow(() -> new OwnerNotFoundException(ownerId));
    }
    @Transactional
    public void deletePet(Long petId){
        petRepository.findById(petId).get().setOwner(null);
        petRepository.delete(petRepository.findById(petId).get());
    }
}
