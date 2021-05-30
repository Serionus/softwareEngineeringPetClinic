package com.io.petclinic.model.repositories;

import com.io.petclinic.model.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAllByBeginTimeAfterAndEndTimeBefore(LocalDateTime beginTime, LocalDateTime endTime);
    List<Visit> findAllByPetPetId(Long petId);
    List<Visit> findAllByVetVetId(Long vetId);
}
