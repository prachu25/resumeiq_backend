package com.resume.analyzer.dto;

public class LoginResponse {

    private String message;
    private String userId;
    private String email;

    public LoginResponse(String message, String userId, String email) {
        this.message = message;
        this.userId = userId;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}