package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.VetNotFoundException;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.repositories.VetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetService {
    private final VetRepository vetRepository;

    public VetService(VetRepository repository) {
        this.vetRepository = repository;
    }

    public List<Vet> findAllVets(){
        return vetRepository.findAll();
    }

    public Vet findVet(Long id){
        return vetRepository.findById(id)
                .orElseThrow( () -> new VetNotFoundException(id));
    }

    public Vet updateVet(Vet newVet, Long id){
        return vetRepository.findById(id)
                .map( vet -> {
                    vet.setFirstname(newVet.getFirstname());
                    vet.setSurname(newVet.getSurname());
                    return vetRepository.save(vet);
                }).orElseGet( () -> {
            newVet.setVetId(id);
            return vetRepository.save(newVet);
        });
    }

    public void deleteVet(Long id){
        vetRepository.deleteById(id);
    }
}
