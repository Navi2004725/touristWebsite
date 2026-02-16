package com.example.touristWebsite.dto;

public class AuthRequestDTO {
    private String email;
    private String password;


    public AuthRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthRequestDTO() {

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
