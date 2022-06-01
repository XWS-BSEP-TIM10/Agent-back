package com.agent.dto;

public class NewUserDto {

    private String email;

    private String password;

    public NewUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public NewUserDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
