package com.example.location_tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequest {
    private @NotBlank String name;
    private @NotBlank String password;
    private @Email @NotBlank String email;
    private @NotBlank String mobile;
    private @NotBlank String address;

    public String getEmail() {
        return email;
    }

    public void setPassword( String password){
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

