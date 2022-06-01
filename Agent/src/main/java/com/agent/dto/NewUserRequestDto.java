package com.agent.dto;

public class NewUserRequestDto {

    private String email;

    private String password;

    public NewUserRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public NewUserRequestDto() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
