package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

//Hania todo
@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private OwnerRepository ownerRepository;

    private PetService underTest;

    // createPet wymaga przekazania ID ownera :)
    private Owner createdOwner;

    @BeforeEach
    void setUp() {
        underTest = new PetService(petRepository, visitRepository, ownerRepository);
        createdOwner = new Owner("Oliver", "Queen");
    }

    @Test
    void canCreatePet() {
        underTest.createPet(createdOwner.getOwnerId(), "McSnurtle", "Turtle");
        ArgumentCaptor<Pet> petArgumentCaptor = ArgumentCaptor.forClass(Pet.class);
        verify(petRepository).save(petArgumentCaptor.capture());
    }

    @Test
    void canFindAllPets() {
        // Given
        List<Pet> expectedList = Arrays.asList(
                new Pet("Hedwig", "Snow Owl", createdOwner),
                new Pet("Pigwidgeon", "Little Owl", createdOwner));
        when(petRepository.findAll()).thenReturn(expectedList);

        // When
        List<Pet> allPets = underTest.findAllPets();

        // Then
        verify(petRepository).findAll();
        // konkretne, opisowe nazwy metod to best thing since sliced bread <3 ~ Hania
        assertThat(allPets).containsExactlyElementsOf(expectedList);

    }

    @Test
    void shouldFindPet() {
        // Given
        Pet expectedPet = new Pet("Lil Sebastian", "Mini Horse", createdOwner);
        when(petRepository.findById(expectedPet.getPetId())).thenReturn(Optional.of(expectedPet));

        // When
        Pet returnedPet = underTest.findPet(expectedPet.getPetId());

        // Then
        assertThat(returnedPet).isEqualTo(expectedPet);
        verify(petRepository).findById(expectedPet.getPetId());
    }


    @Test
    void canUpdateExistingPet() {

        // Given
        Pet petToBeUpdated = new Pet("Shadowfax", "Mearas", createdOwner);
        Pet petExpectedAfterUpdate = new Pet ("Shadowfax Electric Bogaloo", "Pegasus", createdOwner);
        long expectedId = 111L;
        petToBeUpdated.setPetId(expectedId);
        petExpectedAfterUpdate.setPetId(expectedId);

        // When
        when(petRepository.findById(expectedId)).thenReturn(Optional.of(petToBeUpdated));
        when(petRepository.save(petExpectedAfterUpdate)).thenReturn(petExpectedAfterUpdate);

        Pet updatedPet = underTest.updatePet("Shadowfax Electric Bogaloo", "Pegasus", createdOwner, 111L);

        // Then
        assertThat(updatedPet.getName()).isEqualTo("Shadowfax Electric Bogaloo");
        assertThat(updatedPet.getSpecies()).isEqualTo("Pegasus");
        verify(petRepository).findById(petToBeUpdated.getPetId());
        verify(petRepository).save(updatedPet);
    }

    /*
        jestem trochę skonfundowana, biorąc pod uwagę że rzucają ten sam wyjątek
        i przeszukują to samo repozytorium
        chociaż po petID a nie ownerID
        czy ten test jest potrzebny?
    */
    @Test
    void shouldThrowOwnerException() {
        // Given
        when(ownerRepository.findById(1L)).thenThrow(new OwnerNotFoundException(1L));


        // Then
        assertThatThrownBy(() -> underTest.findPet(1L))
                .isInstanceOf(OwnerNotFoundException.class)
                .hasMessageContaining("No such owner with id = 1");

    }

    @Test
    void canDeletePet() {

        // Given
        Pet petToBeDeleted = new Pet("Hedwig", "Snow Owl", createdOwner);
        long expectedId = 1L;

        // When
        when(petRepository.findById(expectedId)).thenReturn(Optional.of(petToBeDeleted));
        doNothing().when(petRepository).deleteByPetId(expectedId);

        underTest.deletePet(expectedId);

        // Then
        verify(petRepository).deleteByPetId(expectedId);

    }
}