package com.io.petclinic;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@CrossOrigin(origins = "", allowedHeaders = "")

@RestController
public class GreetingController {
    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        System.out.println("anotni");
        return "BO Antoniemu latwiej";
    }
}