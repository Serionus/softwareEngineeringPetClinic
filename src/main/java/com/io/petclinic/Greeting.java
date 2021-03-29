package com.io.petclinic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greeting {

    @GetMapping("/")
    public String greeting(){
        return "Tu na razie jest ściernisko, ale będzie weterynaria";
    }
}