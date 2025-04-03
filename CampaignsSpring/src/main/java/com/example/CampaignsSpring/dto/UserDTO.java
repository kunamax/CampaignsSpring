package com.example.CampaignsSpring.dto;

import java.math.BigDecimal;

public class UserDTO {
    private int id;
    private String username;
    private String email;
    private String country;
    private double balance;
    private String phoneNumber;
    private String createdAt;

    public UserDTO(int id, String username, String email, String country, String phoneNumber, String createdAt, double balance) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.country = country;
        this.balance = balance;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }
}
