package com.io.petclinic;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VetController {

    private final VetRepository vetRepository;

    public VetController(VetRepository vetRepository) { this.vetRepository = vetRepository; }

    @GetMapping("/vets")
    List<Vet> all(){
        return vetRepository.findAll();
    }

    @GetMapping("/vets/{id}")
    Vet vet(@PathVariable Long id){
        return vetRepository.findById(id)
                .orElseThrow( () -> new VetNotFoundException(id));
    }

    @PutMapping("/vets/{id}")
    Vet updatedVet(@RequestBody Vet newVet, @PathVariable Long id){
        return vetRepository.findById(id)
                .map( vet -> {
                    vet.setFirstname(newVet.getFirstname());
                    vet.setSurname(newVet.getSurname());
                    return vetRepository.save(vet);
                }).orElseGet( () -> {
                    newVet.setVetId(id);
                    return vetRepository.save(newVet);
                });
    }


    @DeleteMapping("vets/{id}")
    void delete(@PathVariable Long id){
        vetRepository.deleteById(id);
    }
}
