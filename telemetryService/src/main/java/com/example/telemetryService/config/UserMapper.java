package com.example.telemetryService.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserMapper {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
