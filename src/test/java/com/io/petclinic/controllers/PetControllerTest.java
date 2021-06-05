package com.io.petclinic.controllers;

import com.io.petclinic.controllers.entities.PetDTO;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import com.io.petclinic.model.services.OwnerService;
import com.io.petclinic.model.services.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    private PetService petService;

    @Mock
    private OwnerService ownerService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PetRepository petRepository;

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private OwnerRepository ownerRepository;

    private PetController underTest;

    private Owner owner;
    private Pet firstPet;
    private Pet secondPet;

    @BeforeEach
    void setUp() {
        petService = new PetService(petRepository, visitRepository, ownerRepository);
        underTest = new PetController(petService, ownerService, modelMapper);
        owner = new Owner("Bellerophon", "Monsterslayer");
        firstPet = new Pet("Pegasus", "Horse", owner);
        secondPet = new Pet("Chimera", "Hybrid", owner);
    }

    @Test
    void canGetPet() {
        // Given
        long expectedId = firstPet.getPetId();

        // When
        when(petRepository.findById(expectedId)).thenReturn(Optional.of(firstPet));
        PetDTO returnedPet = underTest.getPet(expectedId);

        // Then
        assertThat(returnedPet.getName()).isEqualTo(firstPet.getName());
        assertThat(returnedPet.getSpecies()).isEqualTo(firstPet.getSpecies());

    }

    @Test
    void canCreatePet() {

    }
}