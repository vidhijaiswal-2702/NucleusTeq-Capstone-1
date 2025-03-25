package com.ifms.controller;

import com.ifms.entity.User;


import com.ifms.service.AuthService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Allow requests from frontend (Adjust port if needed)
@CrossOrigin(origins = "http://127.0.0.1:5500")  

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user.getUsername(), user.getPassword(), user.getRole(), user.getEmail());
    }
    
    @PostMapping("/login")
    public User login(@RequestBody User loginRequest) {
        return authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        
    }
    @GetMapping("/user")
    public Optional<User> getLoggedInUser(@RequestParam String email) {
        return authService.getUserByEmail(email);
    }
    
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return authService.getAllUsers();
    }

    }

