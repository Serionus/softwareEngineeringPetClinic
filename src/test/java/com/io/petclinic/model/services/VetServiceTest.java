package com.io.petclinic.model.services;

import com.io.petclinic.exceptions.OwnerNotFoundException;
import com.io.petclinic.exceptions.VetNotFoundException;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.repositories.VetRepository;
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


//fifti fitfi tutti frutti
// spaghetti never forghetti never reghretti

@ExtendWith(MockitoExtension.class)
class VetServiceTest {

    @Mock
    private VetRepository vetRepository;

    private VetService underTest;

    @BeforeEach
    void setUp() {
        underTest = new VetService(vetRepository);
    }

    @Test
    void canCreateVet() {
        underTest.createVet("Diana", "Prince", "test", "test");
        ArgumentCaptor<Vet> vetArgumentCaptor = ArgumentCaptor.forClass(Vet.class);

        verify(vetRepository).save(vetArgumentCaptor.capture());
    }

    @Test
    void canFindAllVets() {
        // Given
        List<Vet> expectedList = Arrays.asList(
                new Vet("Arthur", "Curry", "test", "test"),
                new Vet("Doreen", "Green", "test", "test"));

        // When
        when(vetRepository.findAll()).thenReturn(expectedList);
        List<Vet> allVets = underTest.findAllVets();

        // Then
        verify(vetRepository).findAll();
        assertThat(allVets).containsExactlyElementsOf(expectedList);
    }

    @Test
    void shouldFindVet() {
        // Given
        Vet expectedVet = new Vet("Arthur", "Curry", "test", "test");

        // When
        when(vetRepository.findById(expectedVet.getVetId())).thenReturn(Optional.of(expectedVet));

        Vet returnedVet = underTest.findVet(expectedVet.getVetId());

        // Then
        assertThat(returnedVet).isEqualTo(expectedVet);
        verify(vetRepository).findById(expectedVet.getVetId());

    }

    @Test
    void updateExistingVet() {
        // Given
        Vet vetToBeUpdated = new Vet("Diana", "Prince", "test", "test");
        Vet vetExpectedAfterUpdate = new Vet("Wonder", "Woman", "test", "test");
        long expectedId = 42L;
        vetToBeUpdated.setVetId(expectedId);
        vetExpectedAfterUpdate.setVetId(expectedId);

        // When
        when(vetRepository.findById(expectedId)).thenReturn(Optional.of(vetToBeUpdated));
        when(vetRepository.save(vetExpectedAfterUpdate)).thenReturn(vetExpectedAfterUpdate);

        Vet updatedVet = underTest.updateVet("Wonder", "Woman", 42L);

        // Then
        assertThat(updatedVet.getFirstname()).isEqualTo("Wonder");
        assertThat(updatedVet.getSurname()).isEqualTo("Woman");
        verify(vetRepository).findById(vetToBeUpdated.getVetId());
        verify(vetRepository).save(updatedVet);
    }

    @Test
    void shouldThrowVetException() {
        // Given todo
        when(vetRepository.findById(1L)).thenThrow(new VetNotFoundException(1L));

        // When & Then
        assertThatThrownBy(() -> underTest.findVet(1L))
                .isInstanceOf(VetNotFoundException.class)
                .hasMessageContaining("No such vet with id = 1");

    }

    @Test
    void deleteVet() {
        //Given
        long expectedId = 1L;
        doNothing().when(vetRepository).deleteById(expectedId);

        // When
        underTest.deleteVet(expectedId);

        // Then
        verify(vetRepository).deleteById(expectedId);
    }
}