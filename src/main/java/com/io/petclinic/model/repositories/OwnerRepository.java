package com.io.petclinic.model.repositories;

import com.io.petclinic.model.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository <Owner, Long>{
    Optional<Owner> findOwnerByLoginAndPassword(String login, String password);
    Optional<Owner> findOwnerByLogin(String login);
}
