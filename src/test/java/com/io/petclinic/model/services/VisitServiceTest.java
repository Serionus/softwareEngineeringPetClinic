package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.CannotCreateVisitException;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.entities.Visit;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        // Given / When
        when(vetRepository.findById(createdVet.getVetId())).thenReturn(Optional.of(createdVet));
        when(visitRepository.findAllByBeginTimeAfterAndEndTimeBefore(beginTime, endTime)).thenReturn(Collections.<Visit>emptyList());
        underTest.addVisit(createdVet.getVetId(),beginTime,endTime);
        ArgumentCaptor<Visit> visitArgumentCaptor = ArgumentCaptor.forClass(Visit.class);

        // Then
        verify(visitRepository).save(visitArgumentCaptor.capture());
    }

    @Test
    void cantAddOverlappingVisit() {
        // Given
        List<Visit> visitList = new ArrayList<>();
        Visit conflictVisit = new Visit(createdVet, beginTime, endTime);
        visitList.add(conflictVisit);

//        when(vetRepository.findById(createdVet.getVetId())).thenReturn(Optional.of(createdVet));
        when(visitRepository.findAllByBeginTimeAfterAndEndTimeBefore(beginTime, endTime)).thenReturn(visitList);

        // When / Then

//        verify(visitRepository).save(visitArgumentCaptor.capture());
        assertThatThrownBy(() -> underTest.addVisit(createdVet.getVetId(),beginTime,endTime))
                .isInstanceOf(CannotCreateVisitException.class)
                .hasMessageContaining("There is already an existing visit at that time");

    }

    @Test
    void canAssignPetToVisit() {
        // Given
        Visit emptyVisit = new Visit(createdVet, beginTime, endTime);
        when(visitRepository.findById(1L)).thenReturn(Optional.of(emptyVisit));
        when(petRepository.findById(2L)).thenReturn(Optional.of(createdPet));
        // When
        underTest.assignPetToVisit(2L, 1L);

        // Then
        verify(petRepository).save(createdPet);
        verify(visitRepository).save(emptyVisit);
        //he said he's an expert
        // HE HE
        assertThat(emptyVisit.getPet()).isEqualTo(createdPet);
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
        when(visitRepository.findById(expectedVisit.getVisitId())).thenReturn(Optional.of(expectedVisit));

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
        List<Visit> allVisits = underTest.findAllVisitsByVet(createdVet.getVetId());

        //Then
        verify(visitRepository).findAllByVetVetId(createdVet.getVetId());
        assertThat(allVisits).containsExactlyElementsOf(expectedList);
    }

    @Test
    void shoudFindAllPetVisits() {
        Vet secondVisitVet = new Vet("Otto", "Octavius");
        LocalDateTime beginTimeTwo = LocalDateTime.now().plusDays(3);
        LocalDateTime endTimeTwo = beginTimeTwo.plusMinutes(20);

        Pet assignedPet = new Pet("Cerberus", "Dog", createdOwner);
        assignedPet.setPetId(1L);

        Visit firstVisit =  new Visit(createdVet, beginTime, endTime);
        Visit secondVisit = new Visit(secondVisitVet, beginTimeTwo, endTimeTwo);

        firstVisit.setPet(assignedPet);
        secondVisit.setPet(assignedPet);

        //Given
        List<Visit> expectedList = Arrays.asList(
                firstVisit,
                secondVisit);

        //When
        when(visitRepository.findAllByPetPetId(1L)).thenReturn(expectedList);
        List<Visit> allVisits = underTest.findAllVisitsByPet(1L);

        //Then
        verify(visitRepository).findAllByPetPetId(1L);
        assertThat(allVisits).containsExactlyElementsOf(expectedList);
    }

    @Test
    void canCancelVisit() {
        Visit visitToBeCanceled = new Visit(createdVet, beginTime, endTime);
        visitToBeCanceled.setPet(createdPet);

        when(visitRepository.findById(visitToBeCanceled.getVisitId())).thenReturn(Optional.of(visitToBeCanceled));

        underTest.cancelVisit(visitToBeCanceled.getVisitId());

        verify(visitRepository).save(visitToBeCanceled);
        assertThat(visitToBeCanceled.getPet()).isNull();

    }

    @Test
    void canDeleteVisit() {
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