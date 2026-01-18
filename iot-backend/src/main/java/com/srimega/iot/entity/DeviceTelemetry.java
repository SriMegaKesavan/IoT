package com.srimega.iot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "device_telemetry")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTelemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;

    private Double temperature;
    private Double humidity;
    private Integer battery;

    private LocalDateTime recordedAt;
}

