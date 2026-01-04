package com.srimega.iot.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeviceResponse {

    private Long id;
    private String deviceId;
    private String name;
    private String location;
    private String status;
    private LocalDateTime createdAt;
}
