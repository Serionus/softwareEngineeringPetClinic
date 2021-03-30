package com.io.petclinic;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OwnerController {

    private final OwnerRepository repository;

    public OwnerController(OwnerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/owners")
    List<Owner> all(){
        return repository.findAll();
    }

    @GetMapping("/owners/{id}")
    Owner owner(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow( () -> new OwnerNotFoundException(id));
    }

    @PutMapping("/owners/{id}")
    Owner updatedOwner(@RequestBody Owner newOwner, @PathVariable Long id){
        return repository.findById(id)
                .map( owner -> {
                    owner.setFirstname(newOwner.getFirstname());
                    owner.setSurname(newOwner.getSurname());
                    return repository.save(owner);
                }).orElseGet( () -> {
                    newOwner.setOwnerId(id);
                    return repository.save(newOwner);
                });
    }

    @DeleteMapping("owners/{id}")
    void delete(@PathVariable Long id){
        repository.deleteById(id);
    }
}
