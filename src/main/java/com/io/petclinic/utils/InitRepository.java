package com.io.petclinic.utils;

import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.services.OwnerService;
import com.io.petclinic.model.services.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class InitRepository {
    private static final Logger log = LoggerFactory.getLogger(InitRepository.class);

    @Bean
    CommandLineRunner init(OwnerRepository ownerRepository, PetRepository petRepository){
        PetService petService = new PetService(petRepository);
        OwnerService ownerService = new OwnerService(ownerRepository, petRepository, petService);
        return args -> {
            ownerService.createOwner("Antonio", "Pampersas");
            ownerService.createOwner("Weronika", "Pampersas");
            ownerService.createOwner("Hanna", "Pampersas");
            ownerService.addPet(1L, "Piesio", "Samoyed");
            ownerService.addPet(3L, "Szczerbatek", "Nocna Furia");
            ownerService.addPet(1L, "Olo", "Hipopotam");
            ownerService.addPet(1L, "Rabarbara", "Owca");

            System.out.println("-----------------");
            System.out.println("Find all owners:");
            System.out.println(ownerService.findAllOwners());

            System.out.println("-----------------");
            System.out.println("Update owner...");
            ownerService.updateOwner("Rudolf", "Valentino", 1L);

            System.out.println("-----------------");
            System.out.println("Find all owners: ");
            System.out.println(ownerService.findAllOwners());

            System.out.println("-----------------");
            System.out.println("Find updated owner by id");
            System.out.println(ownerService.findOwner(1L));

            System.out.println("-----------------");
            System.out.println("Find all pets (pet service):");
            System.out.println(petService.findAllPets());

            System.out.println("-----------------");
            System.out.println("Get owner's pet");
            System.out.println(ownerService.getOwnersPet(1L, 4L));

            System.out.println("-----------------");
            System.out.println("Find pet by id: ");
            System.out.println(petService.findPetById(4L));

            System.out.println("-----------------");
            System.out.println("Get all pets by owner's id");
            System.out.println(ownerService.getAllPets(1L));

            System.out.println("-------------------");
            System.out.println("Death of Pampersas");

        };
    }
}
