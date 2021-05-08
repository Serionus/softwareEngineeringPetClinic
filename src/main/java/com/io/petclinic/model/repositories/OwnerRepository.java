package com.io.petclinic.model.repositories;

import com.io.petclinic.model.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository <Owner, Long>{
    public Owner findOwnerByFirstname(String firstname);
}
