package com.api.authservicejavaspringangular.controllers;

import com.api.authservicejavaspringangular.services.AuthorizationService;
import com.api.authservicejavaspringangular.controllers.dtos.AuthenticationDTO;
import com.api.authservicejavaspringangular.controllers.dtos.LoginResponseDTO;
import com.api.authservicejavaspringangular.controllers.dtos.UserInput;
import com.api.authservicejavaspringangular.controllers.dtos.UserOutPut;
import com.api.authservicejavaspringangular.models.user.User;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;
import com.api.authservicejavaspringangular.repository.UserRepository;
import com.api.authservicejavaspringangular.services.TokenService;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthorizationService authorizationService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    public AuthenticationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserInput data) {
/*        if (this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();*/
        User newUser = data.toUser();
        this.repository.save(newUser);
        return ResponseEntity.ok().body(newUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserOutPut>> getAllUsers() {
        List<UserOutPut> users = authorizationService.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<UserOutPut> deleteUser(@PathVariable UUID id){
        authorizationService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutPut> updateUser(@PathVariable UUID id , @RequestBody UserInput userInput){
        Optional<UserOutPut> updatedUser= authorizationService.userUpdate(id, userInput);
        return updatedUser.map(user->ResponseEntity.ok().body(user)).orElse(ResponseEntity.notFound().build());
    }


}