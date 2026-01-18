package com.srimega.iot.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TelemetryResponse {

    private Long id;
    private String deviceId;

    private Double temperature;
    private Double humidity;
    private Integer battery;

    private LocalDateTime recordedAt;
}
