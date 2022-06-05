package com.agent.dto;

public class APITokenResponseDTO {

    private Long id;

    private String apiToken;

    public APITokenResponseDTO(Long id, String apiToken) {
        this.id = id;
        this.apiToken = apiToken;
    }

    public APITokenResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getApiToken() {
        return apiToken;
    }
}
