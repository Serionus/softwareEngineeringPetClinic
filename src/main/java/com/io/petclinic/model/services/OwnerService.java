package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.exceptions.PetNotFoundException;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
//    private VisitRepository visitRepository;

    public OwnerService(OwnerRepository ownerRepository, PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
//        this.visitRepository = visitRepository;
    }

    public List<Owner> findAllOwners(){
        return ownerRepository.findAll();
    }

    public Owner createOwner(String firstname, String surname){
        return new Owner(firstname, surname);
    }

    public Owner findOwner(Long id){
        return ownerRepository.findById(id)
                .orElseThrow( () -> new OwnerNotFoundException(id));
    }

    public Owner updateOwner(Owner newOwner, Long id){
        return ownerRepository.findById(id)
                .map( owner -> {
                    owner.setFirstname(newOwner.getFirstname());
                    owner.setSurname(newOwner.getSurname());
                    return ownerRepository.save(owner);
                }).orElseGet( () -> {
                    newOwner.setOwnerId(id);
                    return ownerRepository.save(newOwner);
                });
    }

    public void deleteOwner(Long id){
        ownerRepository.deleteById(id);
    }


    public Pet getOwnersPet(Long ownerId, Long petId){
        return ownerRepository.findById(ownerId)
                .map( owner -> {
                    return owner.getPetById(petId);

                }).orElseThrow( () -> {
                    return new OwnerNotFoundException(ownerId);
                });
    }

    public void addPet(Long id, Pet newPet){
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new OwnerNotFoundException(id));
        petRepository.save(newPet);

    }
}
