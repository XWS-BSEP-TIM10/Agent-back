package com.agent.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

    private String code;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCode() {
        return code;
    }
}
