package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository repository;

    public OwnerService(OwnerRepository repository) {
        this.repository = repository;
    }

    public List<Owner> findAllOwners(){
        return repository.findAll();
    }

    public Owner findOwner(Long id){
        return repository.findById(id)
                .orElseThrow( () -> new OwnerNotFoundException(id));
    }

    public Owner updateOwner(Owner newOwner, Long id){
        return repository.findById(id)
                .map( owner -> {
                    owner.setFirstname(newOwner.getFirstname());
                    owner.setSurname(newOwner.getSurname());
                    return repository.save(owner);
                }).orElseGet( () -> {
                    newOwner.setOwnerId(id);
                    return repository.save(newOwner);
                });
    }

    public void deleteOwner(Long id){
        repository.deleteById(id);
    }
}
