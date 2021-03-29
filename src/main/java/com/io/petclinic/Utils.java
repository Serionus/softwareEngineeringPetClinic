package com.io.petclinic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    @Bean
    CommandLineRunner init(OwnerRepository ownerRepository){
        return args -> {
            ownerRepository.save(new Owner("Antoni", "Karwowski"));
            ownerRepository.save(new Owner("Weronika", "Lugowska"));
            ownerRepository.save(new Owner("Hanna", "Kraska"));

            ownerRepository.findAll()
                    .forEach( owner -> log.info("Dodano do bazy " + owner));
        };
    }
}
