package com.iset.spring_integration.model;


public class ChatResponse {
    private String reply;

    // Constructor
    public ChatResponse(String reply) {
        this.reply = reply;
    }

    // Getters and Setters
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
