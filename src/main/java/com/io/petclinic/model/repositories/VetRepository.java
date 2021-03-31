package com.io.petclinic.model.repositories;

import com.io.petclinic.model.entities.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Long> {
}
