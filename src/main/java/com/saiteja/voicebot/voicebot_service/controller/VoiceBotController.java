package com.saiteja.voicebot.voicebot_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saiteja.voicebot.voicebot_service.dto.ChatRequest;
import com.saiteja.voicebot.voicebot_service.dto.ChatResponse;
import com.saiteja.voicebot.voicebot_service.service.GeminiService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class VoiceBotController {

    private final GeminiService geminiService;

    // Standard constructor dependency injection
    public VoiceBotController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> handleChat(@RequestBody ChatRequest request) {
        if (request == null || request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ChatResponse("Input transcript cannot be empty."));
        }

        try {
            String generatedReply = geminiService.generateVoiceBotResponse(request.getMessage());
            return ResponseEntity.ok(new ChatResponse(generatedReply));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(503).body(new ChatResponse(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(500).body(new ChatResponse("Sorry, my server ran into an issue processing that. Please try again."));
        }
    }
}