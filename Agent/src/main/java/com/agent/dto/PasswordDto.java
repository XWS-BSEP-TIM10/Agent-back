package com.agent.dto;

import com.agent.validator.ValidPassword;

import javax.validation.constraints.NotBlank;

public class PasswordDto {
    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    private String newPassword;
    @NotBlank(message = "Password is mandatory")
    private String repeatedNewPassword;

    public PasswordDto() {
    }

    public PasswordDto(String newPassword, String repeatedNewPassword) {
        this.newPassword = newPassword;
        this.repeatedNewPassword = repeatedNewPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getRepeatedNewPassword() {
        return repeatedNewPassword;
    }

}
