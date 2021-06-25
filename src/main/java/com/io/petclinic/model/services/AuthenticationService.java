package com.io.petclinic.model.services;

import com.io.petclinic.controllers.entities.TokenDTO;
import com.io.petclinic.controllers.entities.UserCredentialsDTO;
import com.io.petclinic.exceptions.*;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.VetRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final OwnerService ownerService;
    private final VetService vetService;

    @Value("${vet.code}")
    private String vetCode;

    public AuthenticationService(OwnerRepository ownerRepository, VetRepository vetRepository, OwnerService ownerService, VetService vetService) {
        this.ownerRepository = ownerRepository;
        this.vetRepository = vetRepository;
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    public TokenDTO loginUser(UserCredentialsDTO userCredentials){
        Optional<Vet> foundedVet = vetRepository.findVetByLoginAndPassword(userCredentials.getLogin(), userCredentials.getPassword());
        if(foundedVet.isPresent()){
            return new TokenDTO("vet", foundedVet.get().getVetId());
        }
        Optional<Owner> foundedOwner = ownerRepository.findOwnerByLoginAndPassword(userCredentials.getLogin(), userCredentials.getPassword());
        if(foundedOwner.isPresent()){
            return new TokenDTO("owner", foundedOwner.get().getOwnerId());
        }
        throw new UserNotFoundException();
    }

    public TokenDTO registerUser(String login, String password, String firstname, String surname, String vetCode){
        if(vetRepository.findVetByLogin(login).isPresent() || ownerRepository.findOwnerByLogin(login).isPresent()){
            throw new UserAlreadyExistsException();
        }
        System.out.println(vetCode);
        System.out.println(this.vetCode);
        if(vetCode.isEmpty()){
            ownerService.createOwner(firstname, surname, login, password);
            Optional<Owner> createdOwner = ownerRepository.findOwnerByLogin(login);
            System.out.println(createdOwner);
            if(createdOwner.isPresent()){
                return new TokenDTO("owner", createdOwner.get().getOwnerId());
            }
            throw new CannotCreateOwnerException();
        } else if (vetCode.equals(this.vetCode)){
            vetService.createVet(firstname, surname, login, password);
            Optional<Vet> createdVet = vetRepository.findVetByLogin(login);
            if (createdVet.isPresent()){
                return new TokenDTO("vet", createdVet.get().getVetId());
            }
            throw new CannotCreateVetException();
        } else {
            throw new WrongVetCodeException();
        }
    }
}
