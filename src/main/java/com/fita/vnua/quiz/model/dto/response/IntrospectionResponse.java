package com.fita.vnua.quiz.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntrospectionResponse {
    private boolean active;
    private String username;
    private String tokenType;
    private long expirationTime;
    private long issuedAt;
    private long notBefore;

    // Additional user information
    private String email;
    private String fullName;
    private String role;

    // Error information
    private String error;
    private String errorDescription;
}