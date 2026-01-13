package com.example.location_tracker.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/home")

public class Dashboard {
    @GetMapping("/dashboard")
    public String dashboard(){
        System.out.println("Dashaboard \n\n\n\n");
        return "Dashboard for authorized users";
    }
}
