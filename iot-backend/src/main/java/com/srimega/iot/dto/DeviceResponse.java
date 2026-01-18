package com.srimega.iot.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class DeviceResponse {

    private Long id;
    private String deviceId;
    private String name;
    private String location;
    private String status;
    private LocalDateTime createdAt;
}
