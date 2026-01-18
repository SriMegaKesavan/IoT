package com.srimega.iot.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeviceDashboardResponse {

    private String deviceId;
    private String name;
    private String location;
    private String status;

    private Double temperature;
    private Double humidity;
    private Integer battery;

    private String health;   // HEALTHY | LOW | CRITICAL
    private LocalDateTime lastUpdated;
}

