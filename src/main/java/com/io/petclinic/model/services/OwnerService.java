package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;

    public OwnerService(OwnerRepository ownerRepository, VisitRepository visitRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
    }

    public void createOwner(String firstname, String surname, String login, String password){
        Owner owner = new Owner(firstname, surname, login, password);
        ownerRepository.save(owner);
    }

    public List<Owner> findAllOwners(){
        return ownerRepository.findAll();
    }

    public Owner findOwner(Long ownerId){
        return ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException(ownerId));
    }

    public Owner updateOwner(String newFirstName, String newSurname, Long ownerId){
        Optional<Owner> ownerToBeUpdated = ownerRepository.findById(ownerId);
        if(!ownerToBeUpdated.isPresent()) {
            Owner updatedOwner = new Owner(newFirstName, newSurname, ownerToBeUpdated.get().getLogin(), ownerToBeUpdated.get().getPassword());
            return ownerRepository.findById(ownerId)
                    .map(owner -> {
                        owner.setFirstname(updatedOwner.getFirstname());
                        owner.setSurname(updatedOwner.getSurname());
                        return ownerRepository.save(owner);
                    }).orElseGet(() -> {
                        updatedOwner.setOwnerId(ownerId);
                        return ownerRepository.save(updatedOwner);
                    });
        }
        throw new OwnerNotFoundException(ownerId);
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
