package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
//        Owner capturedOwner = ownerArgumentCaptor.getValue();
//        assertThat(capturedOwner).isEqualTo(createdOwner);
    }


    @Test
    void canFindAllOwners() {
        //Given
        List<Owner> expectedList = Arrays.asList(
                new Owner("Edward", "Nigma"),
                new Owner("Harvey", "Dent"));
        when(ownerRepository.findAll()).thenReturn(expectedList);

        //When
        List<Owner> allOwners = underTest.findAllOwners();
        //Then
        verify(ownerRepository).findAll();
        assertThat(allOwners).containsExactlyElementsOf(expectedList);

    }

    @Test
    void shouldFindOwner() {
        // Given
        Owner expectedOwner = new Owner("Edward", "Nigma");
        when(ownerRepository.findById(expectedOwner.getOwnerId())).thenReturn(Optional.of(expectedOwner));

        // When
        Owner returnedOwner = underTest.findOwner(expectedOwner.getOwnerId());

        // Then
        assertThat(returnedOwner).isEqualTo(expectedOwner);
        verify(ownerRepository).findById(expectedOwner.getOwnerId());
    }

    @Test
    void shouldThrowException() {
        // Given
        when(ownerRepository.findById(1L)).thenThrow(new OwnerNotFoundException(1L));

       // When w przypadku wyjątków nie robimy tak jak niżej blok When i Then sie pokrywa
//        Owner returnedOwner = underTest.findOwner(1L);
//        // Then
        assertThatThrownBy(() -> underTest.findOwner(1L))
                .isInstanceOf(OwnerNotFoundException.class)
                .hasMessageContaining("No such owner with id = 1");
//        assertThat(returnedOwner).isEqualTo(expectedOwner);
//        verify(ownerRepository).findById(expectedOwner.getOwnerId());
    }

    @Test
    void updateExistingOwner() {
        //Given
        Owner ownerToBeUpdated = new Owner("Edward", "Nigma");
        Owner ownerExpectedAfterUpdate = new Owner("update", "test");
        long excpetedId = 1L;
        ownerToBeUpdated.setOwnerId(excpetedId);
        ownerExpectedAfterUpdate.setOwnerId(excpetedId);

        when(ownerRepository.findById(excpetedId)).thenReturn(Optional.of(ownerToBeUpdated));
        when(ownerRepository.save(ownerExpectedAfterUpdate)).thenReturn(ownerExpectedAfterUpdate);

        //When
        Owner updatedOwner = underTest.updateOwner("update", "test", 1L);

        //Then
        assertThat(updatedOwner.getFirstname()).isEqualTo("update");
        assertThat(updatedOwner.getSurname()).isEqualTo("test");
        verify(ownerRepository).findById(ownerToBeUpdated.getOwnerId());
        verify(ownerRepository).save(updatedOwner);



    }

    @Test
    void deleteOwner() {
         //Given
        Owner ownerToBeDeleted = new Owner("Edward", "Nigma");

        // czy obchodzi nas literowka, chyba nie
        long excpetedId = 1L;

        when(ownerRepository.findById(excpetedId)).thenReturn(Optional.of(ownerToBeDeleted));
        doNothing().when(ownerRepository).deleteById(excpetedId);

        // When
        underTest.deleteOwner(excpetedId);

        // Then
        verify(ownerRepository).deleteById(excpetedId);
    }
}