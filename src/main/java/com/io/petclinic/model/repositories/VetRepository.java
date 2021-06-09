package com.io.petclinic.model.repositories;

import com.io.petclinic.model.entities.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VetRepository extends JpaRepository<Vet, Long> {
    Optional<Vet> findVetByLoginAndPassword(String login, String password);
}
