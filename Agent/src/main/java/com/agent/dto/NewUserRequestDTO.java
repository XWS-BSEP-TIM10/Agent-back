package com.agent.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NewUserRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public NewUserRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public NewUserRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}