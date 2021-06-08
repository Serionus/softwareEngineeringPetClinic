package com.io.petclinic.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import com.io.petclinic.controllers.entities.TokenDTO;
import com.io.petclinic.controllers.entities.UserCredentialsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Value("test")
    private String jwtSecret;


//    @CrossOrigin
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserCredentialsDTO userCredentials) {
//        //funkcja sprawdzająca czy istnieje użytkownik o danym loginie i haśle
//        //i wyciagajaca typ i id usera
//        if (username.equals(userCredentials.getLogin()) && password
//                .equals(userCredentials.getPassword())) {
//            String token = JWT.create()
//                    .withSubject(username)
//                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
//                    .sign(Algorithm.HMAC256(jwtSecret));
//            return ResponseEntity.ok(new TokenDTO(token, type, id));
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }
}
