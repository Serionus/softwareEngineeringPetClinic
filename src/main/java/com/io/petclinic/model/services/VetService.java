package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.exceptions.VetNotFoundException;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.repositories.VetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public void addVisit (Long vetId, int year, int month, int day, int hour, int minutes){
        Vet vet = vetRepository.findById(vetId).orElseThrow(() -> new VetNotFoundException(vetId));
        LocalDateTime wantedDate = LocalDateTime.of(year,month,day,hour,minutes);
        vet.addNewVisit(visitService.findVisitByDate(wantedDate));
        vetRepository.save(vet);
    }

    public void deleteVisit(Long vetId, Long visitId){
        Vet vet = vetRepository.findById(vetId).orElseThrow(() -> new VetNotFoundException(vetId));
        Visit wantedVisit = vet.getVisitById(visitId);
        vet.getVisits().remove(wantedVisit);
        vetRepository.save(vet);
        visitRepository.save(wantedVisit);
    }

}
