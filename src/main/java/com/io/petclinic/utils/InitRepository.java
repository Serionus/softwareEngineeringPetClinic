package com.io.petclinic.utils;

import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import com.io.petclinic.model.services.OwnerService;
import com.io.petclinic.model.services.PetService;
import com.io.petclinic.model.services.VetService;
import com.io.petclinic.model.services.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;


@Configuration
public class InitRepository {
    private static final Logger log = LoggerFactory.getLogger(InitRepository.class);

    @Bean
    CommandLineRunner init(OwnerRepository ownerRepository, PetRepository petRepository,
                           VetRepository vetRepository, VisitRepository visitRepository){
        PetService petService = new PetService(petRepository);
        VisitService visitService = new VisitService(visitRepository, ownerRepository, vetRepository, petRepository);
        OwnerService ownerService = new OwnerService(ownerRepository, petRepository, petService, visitRepository, visitService);
        VetService vetService = new VetService(vetRepository, visitRepository, visitService);

        return args -> {
            ownerService.createOwner("Antonio", "Pampersas");
            ownerService.createOwner("Weronika", "Pampersas");
            ownerService.createOwner("Hanna", "Pampersas");
            ownerService.addPet(1L, "Piesio", "Samoyed");
            ownerService.addPet(3L, "Szczerbatek", "Nocna Furia");
            ownerService.addPet(1L, "Olo", "Hipopotam");
            ownerService.addPet(1L, "Rabarbara", "Owca");


//            System.out.println("-----------------");
//            System.out.println("Find all owners:");
//            System.out.println(ownerService.findAllOwners());
//
//            System.out.println("-----------------");
//            System.out.println("Update owner...");
//            ownerService.updateOwner("Rudolf", "Valentino", 1L);
//
//            System.out.println("-----------------");
//            System.out.println("Find all owners: ");
//            System.out.println(ownerService.findAllOwners());
//
//            System.out.println("-----------------");
//            System.out.println("Find updated owner by id");
//            System.out.println(ownerService.findOwner(1L));
//
//            System.out.println("-----------------");
//            System.out.println("Find all pets (pet service):");
//            System.out.println(petService.findAllPets());
//
//            System.out.println("-----------------");
//            System.out.println("Get owner's pet");
//            System.out.println(ownerService.getOwnersPet(1L, 4L));
//
//            System.out.println("-----------------");
//            System.out.println("Find pet by id: ");
//            System.out.println(petService.findPetById(4L));
//
//            System.out.println("-----------------");
//            System.out.println("Get all pets by owner's id");
//            System.out.println(ownerService.getAllPets(1L));
//
//            System.out.println("-------------------");
//            System.out.println("Death of Pampersas");
            System.out.println("testowańsko visit generatora, ktory dziala dzieki Hani");
            visitService.generateVisits();
            System.out.println(visitService.findAllVisits());

            System.out.println("testowanie veta");
            vetService.createVet("Steve", "Irwin");
            vetService.addVisit(88L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1, 9, 0);
            vetService.addVisit(88L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1, 12, 30);
            vetService.addVisit(88L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 3, 15, 30);
//            System.out.println(vetService.findVet(88L).getVisits());
//            System.out.println("Ciekawe czy sie wywali");
//            System.out.println(visitRepository.findById(8L));
//            System.out.println("Usuwansko");
//            vetService.deleteVisit(88L, 8L);
//            System.out.println(vetService.findVet(88L).getVisits());
//            System.out.println(visitRepository.findById(8L));
//            vetService.addVisit(88L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1, 9, 0);
//            System.out.println(vetService.findVet(88L).getVisits());
            System.out.println("Update wizyty dla żuru");
            vetService.updateVisit(88L, 8L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 2, 9, 0);
            System.out.println(vetService.findVet(88L).getVisits());

            System.out.println("Co ten owner ma");
            System.out.println(ownerService.getAllPets(3L));

            System.out.println("Przypisanie wizyty dla pieska");
            ownerService.addVisit(3L,5L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 4, 14, 0);
            System.out.println(ownerService.findOwner(3L).getPetById(5L).getVisits());

//            System.out.println("była sobie wizyta");
//            ownerService.deleteVisit(3L, 66L, 5L);
//            System.out.println(ownerService.findOwner(3L).getPetById(5L).getVisits());

            System.out.println("Przekładanie wizyty");
            ownerService.addVisit(3L,5L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 2, 11, 30);
            System.out.println(ownerService.findOwner(3L).getPetById(5L).getVisits());

            ownerService.rescheduleVisit(3L, 66L, 5L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 2, 10, 30);
            System.out.println(ownerService.findOwner(3L).getPetById(5L).getVisits());

            System.out.println("Próba z zajętymi terminami");
//            ownerService.addVisit(3L,5L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 2, 11, 30);
//            System.out.println(ownerService.findOwner(3L).getPetById(5L).getVisits());
            ownerService.rescheduleVisit(3L, 29L, 5L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 2, 10, 30);
            System.out.println(ownerService.findOwner(3L).getPetById(5L).getVisits());


        };
    }
}
