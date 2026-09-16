package com.cper.REST_API_DTO_Validation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class updateUserRequestDTO {
    @NotEmpty
    @Size(min=8, max=20)
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Email
    private String updatedEmail;

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
