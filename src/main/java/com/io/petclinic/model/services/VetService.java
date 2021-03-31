package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.VetNotFoundException;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.repositories.VetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetService {
    private final VetRepository repository;

    public VetService(VetRepository repository) {
        this.repository = repository;
    }

    public List<Vet> findAllVets(){
        return repository.findAll();
    }

    public Vet findVet(Long id){
        return repository.findById(id)
                .orElseThrow( () -> new VetNotFoundException(id));
    }

    public Vet updateVet(Vet newVet, Long id){
        return repository.findById(id)
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
