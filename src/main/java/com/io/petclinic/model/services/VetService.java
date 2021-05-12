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

    public void createVet(String firstname, String surname){
        Vet vet = new Vet(firstname, surname);
        vetRepository.save(vet);
    }

    public Vet findVet(Long id){
        return vetRepository.findById(id)
                .orElseThrow( () -> new VetNotFoundException(id));
    }

    public List<Vet> findAllVets(){
        return vetRepository.findAll();
    }

    public Vet updateVet(String newFirstname,String newSurname, Long id){
        Vet updatedVet = new Vet(newFirstname, newSurname);
        return vetRepository.findById(id)
                .map( vet -> {
                    vet.setFirstname(newFirstname);
                    vet.setSurname(newSurname);
                    return vetRepository.save(vet);
                }).orElseGet( () -> {
                    updatedVet.setVetId(id);
                    return vetRepository.save(updatedVet);
        });
    }

    public void deleteVet(Long id){
        vetRepository.deleteById(id);
    }
}
