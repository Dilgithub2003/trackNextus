package com.example.location_tracker.controller;

import com.example.location_tracker.dto.LoginRequest;
import com.example.location_tracker.dto.RegisterRequest;
import com.example.location_tracker.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.DeclareWarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth/user")
@CrossOrigin

public class UserController {

    @Autowired
    private UserService userService; // NO final


    @GetMapping("/home")
    public String home(){
        return "Welcome home";
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest registerRequest){
        //System.out.println(registerRequest);
        return userService.registerRequest(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){
        return userService.loginURequest(loginRequest);
    }
}
