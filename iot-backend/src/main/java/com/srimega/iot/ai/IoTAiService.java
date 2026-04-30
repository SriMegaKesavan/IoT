package com.srimega.iot.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class IoTAiService {

    private static final Logger log = LoggerFactory.getLogger(IoTAiService.class);

    private final RestClient restClient;
    private final String model;

    public IoTAiService(
            @Value("${ollama.base-url:http://localhost:11434}") String baseUrl,
            @Value("${ollama.model:phi3}") String model) {
        this.model = model;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public String askBot(String userMessage, String context) {
        String prompt = """
                You are an AI assistant for an IoT Monitoring Dashboard.
                You analyze sensor data, MQTT messages, device logs, and alerts.
                Always give clear, actionable insights.
                Use the provided context if available.

                Context:
                %s

                User Question: %s
                """.formatted(context, userMessage);

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "prompt", prompt,
                "stream", false,
                "options", Map.of(
                        "temperature", 0.7
                )
        );

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.post()
                    .uri("/api/generate")
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);

            if (response != null) {
                Object text = response.get("response");
                return text != null ? text.toString().trim() : "No response generated.";
            }
            return "No response generated.";

        } catch (RestClientException e) {
            Throwable cause = e;
            StringBuilder msg = new StringBuilder();
            while (cause != null) {
                msg.append(cause.getClass().getSimpleName()).append(": ").append(cause.getMessage()).append(" | ");
                cause = cause.getCause();
            }
            log.error("[AI] Ollama API call failed: {}", msg);
            throw new OllamaClientException("Ollama API error: " + msg, e);
        }
    }
}
