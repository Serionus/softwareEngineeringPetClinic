package com.io.petclinic.model.repositories;

import com.io.petclinic.model.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    public List<Visit> findAllByBeginTimeAfterAndEndTimeBefore(LocalDateTime beginTime, LocalDateTime endTime);
    public List<Visit> findAllByPetPetId(Long petId);
}
