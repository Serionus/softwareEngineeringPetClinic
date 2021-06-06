package com.io.petclinic.model.repositories;

import com.io.petclinic.model.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
