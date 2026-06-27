package com.saiteja.voicebot.voicebot_service.dto;

public class ChatRequest {
    private String message;

    public ChatRequest() {}

    public ChatRequest(String message) {
        this.message = message;
    }

    public String getMessage() { 
        return this.message; 
    }
    
    public void setMessage(String message) { 
        this.message = message; 
    }
}