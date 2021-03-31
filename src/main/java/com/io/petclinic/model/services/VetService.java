package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.VetNotFoundException;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.repositories.VetRepository;
import org.springframework.stereotype.Service;

@Service
public class VetService {
    private final VetRepository repository;

    public VetService(VetRepository repository) {
        this.repository = repository;
    }

    public void findAllVets(){
        repository.findAll();
    }

    public void findVet(Long id){
        repository.findById(id)
                .orElseThrow( () -> new VetNotFoundException(id));
    }

    public void updateVet(Vet newVet, Long id){
        repository.findById(id)
                .map( vet -> {
                    vet.setFirstname(newVet.getFirstname());
                    vet.setSurname(newVet.getSurname());
                    return repository.save(vet);
                }).orElseGet( () -> {
            newVet.setVetId(id);
            return repository.save(newVet);
        });
    }

    public void deleteVet(Long id){
        repository.deleteById(id);
    }
}
