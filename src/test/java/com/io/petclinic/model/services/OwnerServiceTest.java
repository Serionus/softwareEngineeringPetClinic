package com.io.petclinic.model.services;

import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

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
        verify(ownerRepository).findAll();
//        Owner capturedOwner = ownerArgumentCaptor.getValue();
//        assertThat(capturedOwner).isEqualTo(createdOwner);
    }

    @Test
    void canFindAllOwners() {
        List<Owner> expectedList = Arrays.asList(new Owner("dupa", "jasia"));
        when(ownerRepository.findAll()).thenReturn(expectedList);

        //kiedy wywołuję to
        List<Owner> allOwners = underTest.findAllOwners();
        //sprawdzam, czy wykonało się to
        verify(ownerRepository).findAll();
        Assertions.assertThat(allOwners).containsExactlyElementsOf(expectedList);

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