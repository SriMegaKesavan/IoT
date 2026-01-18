package com.srimega.iot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRequest {

    @NotBlank
    private String deviceId;

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotBlank
    private String status;
}
