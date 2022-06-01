package com.agent.dto;

public class NewUserResponseDTO {

    private String id;

    private String email;

    public NewUserResponseDTO(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public NewUserResponseDTO() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
