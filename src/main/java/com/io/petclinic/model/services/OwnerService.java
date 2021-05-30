package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;

    public OwnerService(OwnerRepository ownerRepository, VisitRepository visitRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
    }

    public void createOwner(String firstname, String surname){
        Owner owner = new Owner(firstname, surname);
        ownerRepository.save(owner);
    }

    public List<Owner> findAllOwners(){
        return ownerRepository.findAll();
    }

    public Owner findOwner(Long ownerId){
        return ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException(ownerId));
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
        for (Pet pet:
             findOwner(id).getPets()) {
            for (Visit visit: pet.getVisits()) {
                visit.setPet(null);
                visitRepository.save(visit);
            }
        }
        ownerRepository.deleteById(id);
        //hmm ciekawe czemu
        //hehe
        //25.04 odwieczna zagadka rozwiązana
    }

}
