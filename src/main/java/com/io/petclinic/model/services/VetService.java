package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.VetNotFoundException;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.repositories.VetRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VetService {
    private final VetRepository vetRepository;

    public VetService(VetRepository repository) {
        this.vetRepository = repository;
    }

    public void createVet(String firstname, String surname, String login, String password){
        Vet vet = new Vet(firstname, surname, login, password);
        vetRepository.save(vet);
    }

    public Vet findVet(Long id){
        return vetRepository.findById(id)
                .orElseThrow( () -> new VetNotFoundException(id));
    }

    public List<Vet> findAllVets(){
        return vetRepository.findAll();
    }

    public Vet updateVet(String newFirstname,String newSurname, Long vetId){
        Optional<Vet> vetToBeUpdated = vetRepository.findById(vetId);
        if(vetToBeUpdated.isPresent()) {
            Vet updatedVet = new Vet(newFirstname, newSurname, vetToBeUpdated.get().getLogin(), vetToBeUpdated.get().getPassword());
            return vetRepository.findById(vetId)
                    .map(vet -> {
                        vet.setFirstname(newFirstname);
                        vet.setSurname(newSurname);
                        return vetRepository.save(vet);
                    }).orElseGet(() -> {
                        updatedVet.setVetId(vetId);
                        return vetRepository.save(updatedVet);
                    });
        }
        throw new VetNotFoundException(vetId);
    }

    public void deleteVet(Long id){
        vetRepository.deleteById(id);
    }
}
