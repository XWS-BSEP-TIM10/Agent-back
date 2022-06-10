package com.agent.dto;

import com.agent.validator.BlackList;
import com.agent.validator.FieldMatch;
import com.agent.validator.PasswordContainsEmail;
import com.agent.validator.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@PasswordContainsEmail(password = "password", email = "email", message = "The password can not contain email")
public class NewUserRequestDTO {

    @NotBlank
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    @BlackList
    private String password;

    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
