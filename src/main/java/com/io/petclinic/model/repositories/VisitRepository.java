package com.io.petclinic.model.repositories;

import com.io.petclinic.model.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    // sprawdź czy istnieją już zarezerwowane wizyty w przedziale czasowym wizyty do umówieniafin
    List<Visit> findAllByBeginTimeAfterAndEndTimeBefore(LocalDateTime beginTime, LocalDateTime endTime);

    // umawiamy dwójkę między 1 i 3
    //  endtime1 < begintime2-endtime2 < begintime3
//    List<Visit> findAllByEndTimeAfterAndBeginTimeBefore(LocalDateTime endTime, LocalDateTime beginTime);
    List<Visit> findAllByPetPetId(Long petId);
    List<Visit> findAllByVetVetId(Long vetId);
    // sprawdzenie tego nam nic nie daje bo to tylko sprawdza czy w ogóle istnieją wizyty poza ramami
    // czasowymi tego co chcemy umówić
//    List<Visit> findAllByBeginTimeBeforeAndEndTimeAfter();

}
