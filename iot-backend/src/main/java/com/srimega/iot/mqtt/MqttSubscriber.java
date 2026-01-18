package com.srimega.iot.mqtt;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.srimega.iot.dto.DeviceRequest;
import com.srimega.iot.dto.TelemetryRequest;
import com.srimega.iot.service.DeviceService;
import com.srimega.iot.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MqttSubscriber {

    private final MqttClient mqttClient;
    private final DeviceService deviceService;
    private final TelemetryService telemetryService;
    private final JsonMapper jsonMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void subscribe() {
        try {
            if (!mqttClient.isConnected()) {
                log.warn("⚠ MQTT client not connected yet");
                return;
            }

            log.info("📡 Subscribing to topics: iot/devices/#");

            mqttClient.subscribe("iot/devices/#", (topic, message) -> {
                String payload = new String(message.getPayload());
                log.info("📩 MQTT message | topic={} | payload={}", topic, payload);

                try {
                    routeMessage(topic, payload);
                } catch (Exception e) {
                    log.error("❌ Failed to process MQTT message", e);
                }
            });

        } catch (Exception e) {
            log.error("❌ MQTT subscription error", e);
        }
    }

    private void routeMessage(String topic, String payload) throws Exception {

        if (topic.equals("iot/devices/register")) {
            DeviceRequest request =
                    jsonMapper.readValue(payload, DeviceRequest.class);
            deviceService.register(request);
            log.info("✅ Device registered: {}", request.getDeviceId());

        } else if (topic.equals("iot/devices/status")) {
            DeviceRequest request =
                    jsonMapper.readValue(payload, DeviceRequest.class);
            deviceService.updateStatus(request);
            log.info("🔄 Device status updated: {}", request.getDeviceId());
        } else if (topic.startsWith("iot/devices/telemetry")) {
                TelemetryRequest request =
                        jsonMapper.readValue(payload, TelemetryRequest.class);
                telemetryService.save(request);
        } else {
            log.warn("⚠ Ignored unknown topic: {}", topic);
        }
    }
}
