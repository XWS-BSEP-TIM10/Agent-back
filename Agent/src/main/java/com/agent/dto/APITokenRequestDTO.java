package com.agent.dto;

import javax.validation.constraints.NotBlank;

public class APITokenRequestDTO {

    @NotBlank
    private String apiToken;

    public APITokenRequestDTO(String apiToken) {
        this.apiToken = apiToken;
    }

    public APITokenRequestDTO() {
    }

    public String getApiToken() {
        return apiToken;
    }
}
