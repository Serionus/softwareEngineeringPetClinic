package com.io.petclinic.model.repositories;

import com.io.petclinic.model.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
