package com.io.petclinic.model.repositories;

import com.io.petclinic.model.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    public Pet findPetByOwnerOwnerId(Long ownerId);
    public List<Pet> findPetsByOwnerOwnerId(Long ownerId);
}
