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

public class LoginRequest {
    private @NotNull @Email String email;
    private @NotNull String password;

    public String getEmail() {
        return email;
    }

    public CharSequence getPassword() {
        return password;
    }
}
