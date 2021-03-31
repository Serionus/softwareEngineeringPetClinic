package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.PetNotFoundException;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> findAllPets(){
        return repository.findAll();
    }

    public Pet findPetById(Long id){
        return repository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    }

    public Pet createPet(String name, String species){
        return new Pet(name, species);
    }

    public Pet savePetToRepository(Pet newPet){
        return repository.save(newPet);
    }
}
