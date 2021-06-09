package com.io.petclinic.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import com.io.petclinic.controllers.entities.TokenDTO;
import com.io.petclinic.controllers.entities.UserCredentialsDTO;
import com.io.petclinic.exceptions.UserNotFoundException;
import com.io.petclinic.model.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Value("test")
    private String jwtSecret;


    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserCredentialsDTO userCredentials) {
        //funkcja sprawdzająca czy istnieje użytkownik o danym loginie i haśle
        //i wyciagajaca typ i id usera
        try {
            TokenDTO tokenDTO = authenticationService.returnUserRoleAndId(userCredentials);
            String token = JWT.create()
                    .withSubject(tokenDTO.getType())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                    .sign(Algorithm.HMAC256(jwtSecret));
            tokenDTO.setToken(token);
            return ResponseEntity.ok(tokenDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
