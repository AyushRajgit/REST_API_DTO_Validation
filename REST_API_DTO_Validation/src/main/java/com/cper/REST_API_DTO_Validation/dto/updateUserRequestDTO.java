package com.cper.REST_API_DTO_Validation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class updateUserRequestDTO {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min=8, max=20, message = "Length of username must be between 8 to 20")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email ID")
    private String email;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email ID")
    private String updatedEmail;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min=8, max=20, message = "Length of password must be between 8 to 20")
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

    public String getUpdatedEmail() {
        return updatedEmail;
    }

    public void setUpdatedEmail(String updatedEmail) {
        this.updatedEmail = updatedEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
