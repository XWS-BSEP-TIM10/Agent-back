package com.agent.dto;

import com.agent.validator.BlackList;
import com.agent.validator.FieldMatch;
import com.agent.validator.PasswordContainsEmail;
import com.agent.validator.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@PasswordContainsEmail(password = "password", email = "email", message = "The password can not contain email")
public class ChangePasswordDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "Old password is mandatory")
    private String oldPassword;

    @NotBlank(message = "New password is mandatory")
    @ValidPassword
    @BlackList
    private String newPassword;

    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String email, String oldPassword, String newPassword, String confirmPassword) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
