package com.io.petclinic.model.services;

import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {
    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private VisitRepository visitRepository;

//    private AutoCloseable autoCloseable;

    private OwnerService underTest;

    @BeforeEach
    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new OwnerService(ownerRepository, visitRepository);
    }

//    @AfterEach
//    void tearDown() throws Exception{
//        autoCloseable.close();
//    }

    @Test
    void canCreateOwner() {
        //gdybysmy przekazywali ownera to bysmy odkomentowali
//        Owner createdOwner = new Owner("Bruce", "Wayne");
        underTest.createOwner("Bruce", "Wayne");
        ArgumentCaptor<Owner> ownerArgumentCaptor = ArgumentCaptor.forClass(Owner.class);
        verify(ownerRepository).save(ownerArgumentCaptor.capture());
//        Owner capturedOwner = ownerArgumentCaptor.getValue();
//        assertThat(capturedOwner).isEqualTo(createdOwner);
    }

    @Test
    void canFindAllOwners() {
        //kiedy wywołuję to
        underTest.findAllOwners();
        //sprawdzam, czy wykonało się to
        verify(ownerRepository).findAll();
    }

    @Test
    void findOwner() {
    }

    @Test
    void updateOwner() {
    }

    @Test
    void deleteOwner() {
    }
}