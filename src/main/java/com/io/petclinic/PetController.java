package com.io.petclinic;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController {

    private final PetRepository repository;

    public PetController(PetRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<Pet> all() { return repository.findAll();}

//    @PostMapping("/owners/{id}/pets/")
//    Pet pet

}


// @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
//  public String submit(@ModelAttribute("employee") Employee employee) {
//    // Code that uses the employee object
//
//    return "employeeView";
//}
