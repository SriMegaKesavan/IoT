package com.srimega.iot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeviceRequest {

    @NotBlank
    private String deviceId;

    @NotBlank
    private String name;

    private String location;

    private String status;
}
