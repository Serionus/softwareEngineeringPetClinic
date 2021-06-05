package com.io.petclinic.model.services;

import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import jdk.vm.ci.meta.Local;
import org.assertj.core.annotations.Beta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private VetRepository vetRepository;

    @Mock
    private PetRepository petRepository;

    private VisitService underTest;
    private Vet createdVet;
    private Pet createdPet;
    private Owner createdOwner;
    public LocalDateTime beginTime;
    public LocalDateTime endTime;
//    public LocalDate presentDate;
//    public LocalTime presentTime;

    @BeforeEach
    void setUp() {
        underTest = new VisitService(visitRepository, vetRepository, petRepository);
        createdVet = new Vet("Eliza", "Doolittle");
        createdOwner = new Owner("Geralt", "Gwynbleidd");
        createdPet = new Pet("Roach", "Horse", createdOwner);

        beginTime = LocalDateTime.now();
        // już nie pamiętam jak długie powinny być wizyty więc opcja bezpieczna
        endTime = beginTime.plusMinutes(20);
    }

    @Test
    void canAddVisit() {
        underTest.addVisit(createdVet.getVetId(),beginTime,endTime);
        ArgumentCaptor<Visit> visitArgumentCaptor = ArgumentCaptor.forClass(Visit.class);

        verify(visitRepository).save(visitArgumentCaptor.capture());
    }

    @Test
    void canAssignPetToVisit() {
        // Given
        Visit emptyVisit = new Visit(createdVet, beginTime, endTime);
        emptyVisit.setPet(createdPet);

        // When
        Visit fullVisit = underTest.findVisitById(emptyVisit.getVisitId());
        Pet assignedPet = fullVisit.getPet();

        // Then
        assertThat(assignedPet).isEqualTo(createdPet);
    }

    @Test
    void canFindAllVisitsByVisitId() {
        Vet secondVisitVet = new Vet("Otto", "Octavius");
        LocalDateTime beginTimeTwo = LocalDateTime.now().plusDays(3);
        LocalDateTime endTimeTwo = beginTimeTwo.plusMinutes(20);

        //Given
        List<Visit> expectedList = Arrays.asList(
                new Visit(createdVet, beginTime, endTime),
                new Visit(secondVisitVet, beginTimeTwo, endTimeTwo));

        //When
        when(visitRepository.findAll()).thenReturn(expectedList);
        List<Visit> allVisits = underTest.findAllVisits();

        //Then
        verify(visitRepository).findAll();
        assertThat(allVisits).containsExactlyElementsOf(expectedList);

    }

    @Test
    void shouldFindVisitById() {
        // Given
        Visit expectedVisit = new Visit(createdVet, beginTime, endTime);

        // When
        Visit returnedVisit = underTest.findVisitById(expectedVisit.getVisitId());


        // Then
        assertThat(returnedVisit).isEqualTo(expectedVisit);
        verify(visitRepository).findById(expectedVisit.getVisitId());
    }

    @Test
    void updateVisit() {
    }

    @Test
    void shouldFindAllVetVisits() {
        LocalDateTime beginTimeTwo = LocalDateTime.now().plusDays(3);
        LocalDateTime endTimeTwo = beginTimeTwo.plusMinutes(20);

        Visit firstVisit =  new Visit(createdVet, beginTime, endTime);
        Visit secondVisit = new Visit(createdVet, beginTimeTwo, endTimeTwo);


        //Given
        List<Visit> expectedList = Arrays.asList(
                firstVisit,
                secondVisit);

        //When
        when(visitRepository.findAllByVetVetId(createdVet.getVetId())).thenReturn(expectedList);
        List<Visit> allVisits = underTest.findAllVisits();

        //Then
        verify(visitRepository).findAll();
        assertThat(allVisits).containsExactlyElementsOf(expectedList);
    }

    @Test
    void shoudFindAllPetVisits() {
        Vet secondVisitVet = new Vet("Otto", "Octavius");
        LocalDateTime beginTimeTwo = LocalDateTime.now().plusDays(3);
        LocalDateTime endTimeTwo = beginTimeTwo.plusMinutes(20);

        Pet assignedPet = new Pet("Cerberus", "Dog", createdOwner);

        Visit firstVisit =  new Visit(createdVet, beginTime, endTime);
        Visit secondVisit = new Visit(secondVisitVet, beginTimeTwo, endTimeTwo);

        firstVisit.setPet(assignedPet);
        secondVisit.setPet(assignedPet);

        //Given
        List<Visit> expectedList = Arrays.asList(
                firstVisit,
                secondVisit);

        //When
        when(visitRepository.findAllByPetPetId(assignedPet.getPetId())).thenReturn(expectedList);
        List<Visit> allVisits = underTest.findAllVisits();

        //Then
        verify(visitRepository).findAll();
        assertThat(allVisits).containsExactlyElementsOf(expectedList);
    }

    @Test
    void cancelVisit() {
    }

    @Test
    void deleteVisit() {
        //Given
        Visit visitToBeDeleted = new Visit(createdVet, beginTime, endTime);

        long expectedId = 1L;

        // When
        when(visitRepository.findById(expectedId)).thenReturn(Optional.of(visitToBeDeleted));
        doNothing().when(visitRepository).deleteById(expectedId);


        underTest.deleteVisit(expectedId);

        // Then
        verify(visitRepository).deleteById(expectedId);
    }
}