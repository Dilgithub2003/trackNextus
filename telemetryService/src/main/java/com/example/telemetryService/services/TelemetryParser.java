package com.example.telemetryService.services;


import com.example.telemetryService.dto.TelemetryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class TelemetryParser {

    private final ObjectMapper mapper;

    public TelemetryParser() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());       // ⬅ Enable Instant, LocalDateTime, etc.
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    public String toJson(TelemetryDTO dto) throws Exception {
        return mapper.writeValueAsString(dto);
    }
    public TelemetryDTO parse(String json) throws Exception {
        return mapper.readValue(json, TelemetryDTO.class);
    }
    
}


