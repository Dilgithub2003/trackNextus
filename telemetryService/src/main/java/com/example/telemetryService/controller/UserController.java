package com.example.telemetryService.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/location")
public class UserController {
    @PostMapping("/start")
    public String start(){
        return "Started location sharing";
    }
}
