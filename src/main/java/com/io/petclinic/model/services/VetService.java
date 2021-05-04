package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.CannotCreateVisitException;
import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.exceptions.VetNotFoundException;
import com.io.petclinic.exceptions.VisitNotFoundException;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.repositories.VetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VetService {
    private final VetRepository vetRepository;
    private final VisitRepository visitRepository;
    private final VisitService visitService;

    public VetService(VetRepository repository, VisitRepository visitRepository, VisitService visitService) {
        this.vetRepository = repository;
        this.visitRepository = visitRepository;
        this.visitService = visitService;
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
