package com.example.location_tracker.services;

import com.example.location_tracker.dto.LoginRequest;
import com.example.location_tracker.dto.RegisterRequest;
import com.example.location_tracker.entity.User;
import com.example.location_tracker.repositories.UserRepository;
import com.example.location_tracker.security.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerRequest(@NotNull RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()) != null) {
            return "Email already exists!";
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = modelMapper.map(request, User.class);
        userRepository.save(user);
        return "Registration sucess";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginURequest(@Valid @RequestBody LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "email", user.getEmail(),
                        "name", user.getName()
                )
        );
    }


}
