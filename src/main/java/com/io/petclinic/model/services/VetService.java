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

    public List<Vet> findAllVets(){
        return vetRepository.findAll();
    }

    public void createVet(String firstname, String surname){
        Vet vet = new Vet(firstname, surname);
        vetRepository.save(vet);
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

    public void addVisit (Long vetId, LocalDateTime beginTime, LocalDateTime endTime){
        if(visitRepository.findAllByBeginTimeAfterAndEndTimeBefore(beginTime, endTime).size() == 0){
            visitRepository.save(new Visit(vetRepository.findById(vetId).orElseThrow(() -> new VetNotFoundException(vetId)), beginTime, endTime));
        } else {
            throw new CannotCreateVisitException();
        }
    }

    public void deleteVisit(Long vetId, Long visitId){
        Vet vet = vetRepository.findById(vetId).orElseThrow(() -> new VetNotFoundException(vetId));
        Optional<Visit> wantedVisit = visitRepository.findById(visitId);
        if(wantedVisit.isPresent()){
            vet.getVisits().remove(wantedVisit.get());
            vetRepository.save(vet);
            visitRepository.save(wantedVisit.get());
        } else {
            throw new VisitNotFoundException(visitId);
        }
    }

}
