package com.example.location_tracker.controller;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DBTestController {

    @PersistenceContext
    private EntityManager entityManager;
    @RequestMapping("/auth/user")
    @GetMapping("/db-test")
    public String testDatabase() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return "✅ Database Connection Successful! \n \n \n \n \n";
        } catch (Exception e) {
            return "❌ Database Connection Failed: " + e.getMessage();
        }
    }
}
