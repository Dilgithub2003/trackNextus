package com.example.gps_tracking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class DeviceInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String deviceID;

    @Column(nullable = false)
    private String securityKey;

    @Column(nullable = false)
    private String secretKey;

    @Column(nullable = true)
    private int imei;

    @Column(nullable = false)
    private boolean isActive = false;

    @Column(nullable = true)
    private LocalDateTime registeredAt = LocalDateTime.now();

    @Column(nullable = true)
    private String userEmail;
}
