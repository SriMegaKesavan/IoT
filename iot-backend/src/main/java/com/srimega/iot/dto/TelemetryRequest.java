package com.srimega.iot.dto;

import lombok.Data;

@Data
public class TelemetryRequest {
    private String deviceId;
    private Double temperature;
    private Double humidity;
    private Integer battery;
}