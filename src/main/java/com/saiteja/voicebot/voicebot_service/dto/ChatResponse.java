package com.saiteja.voicebot.voicebot_service.dto;

public class ChatResponse {
    private String reply;

    public ChatResponse() {}

    public ChatResponse(String reply) {
        this.reply = reply;
    }

    public String getReply() { 
        return this.reply; 
    }

    public void setReply(String reply) { 
        this.reply = reply; 
    }
}