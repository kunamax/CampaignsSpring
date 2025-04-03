package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private int id;

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "PhoneNumber", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "Balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Campaign> campaigns;

    @ManyToOne
    @JoinColumn(name = "CountryISO2Code", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Product> products;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
