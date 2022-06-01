package com.agent.dto;

public class NewUserResponseDto {

    private String id;

    private String email;

    public NewUserResponseDto(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public NewUserResponseDto() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
