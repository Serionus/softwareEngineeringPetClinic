package com.io.petclinic.utils;

import com.io.petclinic.model.repositories.OwnerRepository;
import com.io.petclinic.model.repositories.PetRepository;
import com.io.petclinic.model.repositories.VetRepository;
import com.io.petclinic.model.repositories.VisitRepository;
import com.io.petclinic.model.services.OwnerService;
import com.io.petclinic.model.services.PetService;
import com.io.petclinic.model.services.VetService;
import com.io.petclinic.model.services.VisitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;


@Configuration
public class InitRepository {

    @Bean
    CommandLineRunner init(OwnerRepository ownerRepository, PetRepository petRepository,
                           VetRepository vetRepository, VisitRepository visitRepository){

        OwnerService ownerService = new OwnerService(ownerRepository, visitRepository);
        PetService petService = new PetService(petRepository, visitRepository, ownerRepository);
        VisitService visitService = new VisitService(visitRepository, vetRepository, petRepository);
        VetService vetService = new VetService(vetRepository);

        return args -> {
            System.out.println("-----------------");
            System.out.println("Find all owners without pets:");
            ownerService.createOwner("Antonio", "Pampersas");
            ownerService.createOwner("Weronika", "Pampersas");
            ownerService.createOwner("Hanna", "Pampersas");
            System.out.println(ownerService.findAllOwners());

            petService.createPet(1L, "Piesio", "Samoyed");
            petService.createPet(1L, "Olo", "Hipopotam");
            petService.createPet(1L, "Rabarbara", "Owca");
            petService.createPet(3L, "Szczerbatek", "Nocna Furia");

            System.out.println("-----------------");
            System.out.println("Find all pets:");
            System.out.println(petService.findAllPets());

            System.out.println("-----------------");
            System.out.println("Get all pets by owner's id:");
            System.out.println(petService.getAllPets(1L));

            System.out.println("-----------------");
//            System.out.println("Update owner...");
//            ownerService.updateOwner("Rudolf", "Valentino", 1L);

            System.out.println("-----------------");
            System.out.println("Find all owners: ");
            System.out.println(ownerService.findAllOwners());
            System.out.println(petService.getAllPets(1L));

            System.out.println("-----------------");
            System.out.println("Find pet by id: ");
            System.out.println(petService.findPet(4L));

            System.out.println("-----------------");
            System.out.println("Update pet: ");
            System.out.println(petService.updatePet("Sylwester", "Kot", ownerService.findOwner(1L), 4L));
            System.out.println("-----------------");
            System.out.println("Find pet by id: ");
            System.out.println(petService.findPet(4L));

            System.out.println("-----------------");
            System.out.println("Rip pet ");
            petService.deletePet(4L);
            System.out.println(petService.findAllPets());
            System.out.println("-----------------");
////            System.out.println("Find pet by id: ");
////            System.out.println(petService.findPet(4L));
//            System.out.println("-------------------");
//            System.out.println("Death of Pampersas");
//            ownerService.deleteOwner(1L);
//            System.out.println(ownerService.findAllOwners());
//            System.out.println(petService.findAllPets());

////            System.out.println("testowańsko visit generatora, ktory dziala dzieki Hani");
//            //rip visit generator ale nadal dzięki Haniu

            System.out.println("testowanie veta");
            vetService.createVet("Steve", "Irwin");
            vetService.createVet("Wombat", "Skansen");
            System.out.println(vetService.findAllVets());
            vetService.updateVet("Horben", "Kansen", 9L);
            System.out.println(vetService.findAllVets());
            LocalDateTime teraz = LocalDateTime.now();
            LocalDateTime potem = LocalDateTime.now().plusHours(1);
            visitService.addVisit(8L, teraz, potem);
            visitService.addVisit(8L, teraz, potem);
            visitService.addVisit(9L, teraz, potem);
            System.out.println(visitService.findAllVisits());
            visitService.assignPetToVisit(5L, 10L);

            System.out.println("\n");
//            System.out.println(visitService.findAllVisits());
//            System.out.println("\n");
//            System.out.println("testy usuniec");
//            System.out.println("\n");
////            vetService.deleteVet(8L);
////            petService.deletePet(7L);
////            ownerService.deleteOwner(3L);
////            visitService.deleteVisit(10L);
////            System.out.println("losto caradhras, sedho, hodo, nuitho i ruith!");
//            System.out.println(ownerService.findAllOwners());
//            System.out.println(petService.findAllPets());
//            System.out.println(visitService.findAllVisits());
//            System.out.println(vetService.findAllVets());
//            System.out.println("delete wombat");
//            System.out.println(vetService.findAllVets());
        };
    }
}
