package com.cper.REST_API_DTO_Validation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class createUserRequestDTO {
    @NotEmpty
    @Size(min=3, max=30)
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min=8, max=20)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
