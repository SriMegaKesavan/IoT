package com.srimega.iot.ai;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final IoTAiService aiService;
    private final IoTContextProvider contextProvider;

    @PostMapping("/ask")
    public ResponseEntity<String> ask(@RequestBody Map<String, String> body) {

        String question = body.get("question");
        String context = contextProvider.buildContext();

        String answer = aiService.askBot(question, context);

        return ResponseEntity.ok(answer);
    }
}

