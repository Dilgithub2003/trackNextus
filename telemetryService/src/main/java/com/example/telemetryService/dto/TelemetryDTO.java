package com.example.telemetryService.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class TelemetryDTO {

    /* ---------------- Device Identity ---------------- */

    @JsonAlias({"deviceId", "deviceID"})
    @NotBlank
    private String deviceId;

    @NotBlank
    private String secretKey;

    /* ---------------- Location ---------------- */

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    /* ---------------- Motion & Status ---------------- */

    private Double speed;

    private Boolean accident;
    private Boolean rollover;
    private Boolean overspeed;
    private Boolean harshBrake;

    private Double tiltAngle;

    /* ---------------- Time ---------------- */

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    @NotNull
    private Instant timestamp;

    /* ---------------- Getters & Setters ---------------- */

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Boolean getAccident() {
        return accident;
    }

    public void setAccident(Boolean accident) {
        this.accident = accident;
    }

    public Boolean getRollover() {
        return rollover;
    }

    public void setRollover(Boolean rollover) {
        this.rollover = rollover;
    }

    public Boolean getOverspeed() {
        return overspeed;
    }

    public void setOverspeed(Boolean overspeed) {
        this.overspeed = overspeed;
    }

    public Boolean getHarshBrake() {
        return harshBrake;
    }

    public void setHarshBrake(Boolean harshBrake) {
        this.harshBrake = harshBrake;
    }

    public Double getTiltAngle() {
        return tiltAngle;
    }

    public void setTiltAngle(Double tiltAngle) {
        this.tiltAngle = tiltAngle;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
