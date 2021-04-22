package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.PetNotFoundException;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository repository) {
        this.petRepository = repository;
    }

    public List<Pet> findAllPets(){
        return petRepository.findAll();
    }

    public Pet findPetById(Long id){
        return petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    }

    public Pet createPet(String name, String species){
        Pet newPet = new Pet(name, species);
        petRepository.save(newPet);
        return newPet;
    }

    public void deleteVisit(Long visitId){

    }
}
