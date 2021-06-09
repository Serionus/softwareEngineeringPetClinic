package com.io.petclinic.model.services;

import com.io.petclinic.controllers.entities.TokenDTO;
import com.io.petclinic.controllers.entities.UserCredentialsDTO;
import com.io.petclinic.exceptions.UserNotFoundException;
import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Vet;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.VetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;

    public AuthenticationService(OwnerRepository ownerRepository, VetRepository vetRepository) {
        this.ownerRepository = ownerRepository;
        this.vetRepository = vetRepository;
    }

    public TokenDTO returnUserRoleAndId(UserCredentialsDTO userCredentials){
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
}
