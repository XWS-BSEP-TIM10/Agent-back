package com.agent.dto;

public class TwoFAResponseDTO {
    private String secret;

    public TwoFAResponseDTO(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}
