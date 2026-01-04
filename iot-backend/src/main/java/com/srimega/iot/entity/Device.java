package com.srimega.iot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String deviceId;

    private String name;
    private String location;
    private String status;

    private LocalDateTime createdAt;
}
