package com.io.petclinic.utils;

import com.io.petclinic.model.entities.Owner;
import com.io.petclinic.model.entities.Pet;
import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class InitRepository {
    private static final Logger log = LoggerFactory.getLogger(InitRepository.class);

    @Bean
    CommandLineRunner init(OwnerRepository ownerRepository){
        return args -> {
            Owner test = new Owner("Jacek", "Rogowski");
            Pet piesio = new Pet("Piesio");
            ownerRepository.save(new Owner("Antoni", "Karwowski")).addNewPet(piesio);
            ownerRepository.save(new Owner("Weronika", "Lugowska"));
            ownerRepository.save(new Owner("Hanna", "Kraska"));


            ownerRepository.findAll()
                    .forEach( owner -> log.info("Dodano do bazy " + owner));

        };
    }
}
