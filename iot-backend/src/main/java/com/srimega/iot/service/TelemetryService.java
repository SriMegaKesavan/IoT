package com.srimega.iot.service;

import com.srimega.iot.dto.TelemetryRequest;
import com.srimega.iot.dto.TelemetryResponse;
import com.srimega.iot.entity.DeviceTelemetry;

import java.util.List;

public interface TelemetryService {

    void save(TelemetryRequest request);

    List<TelemetryResponse> getByDevice(String deviceId);

    TelemetryResponse getLatest(String deviceId);
}