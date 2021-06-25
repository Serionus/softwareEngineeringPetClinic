package com.io.petclinic.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

import com.io.petclinic.controllers.entities.HumanDTO;
import com.io.petclinic.controllers.entities.TokenDTO;
import com.io.petclinic.controllers.entities.UserCredentialsDTO;
import com.io.petclinic.exceptions.*;
import com.io.petclinic.model.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Value("${jwt.secret}")
    private String jwtSecret;


    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserCredentialsDTO userCredentials) {
        //funkcja sprawdzająca czy istnieje użytkownik o danym loginie i haśle
        //i wyciagajaca typ i id usera
        try {
            TokenDTO tokenDTO = authenticationService.loginUser(userCredentials);
            String token = JWT.create()
                    .withSubject(userCredentials.getLogin())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                    .sign(Algorithm.HMAC256(jwtSecret));
            tokenDTO.setToken(token);
            return ResponseEntity.ok(tokenDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody HumanDTO humanDTO){
        try{
            TokenDTO tokenDTO = authenticationService.registerUser(humanDTO.getLogin(), humanDTO.getPassword(), humanDTO.getFirstname(), humanDTO.getSurname(), humanDTO.getVetCode());
            String token = JWT.create()
                    .withSubject(humanDTO.getLogin())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                    .sign(Algorithm.HMAC256(jwtSecret));
            tokenDTO.setToken(token);
            return ResponseEntity.ok(tokenDTO);
        } catch(WrongVetCodeException e){
            return ResponseEntity.status(520).build();
        } catch (CannotCreateOwnerException e){
            return  ResponseEntity.status(521).build();
        } catch (CannotCreateVetException e){
            return  ResponseEntity.status(522).build();
        } catch (UserAlreadyExistsException e){
            return  ResponseEntity.status(523).build();
        }

    }
}
