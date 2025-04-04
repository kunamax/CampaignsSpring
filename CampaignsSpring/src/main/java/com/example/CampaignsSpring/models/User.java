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

    @Column(name = "Name", nullable = false, unique = true, length = 30)
    private String name;

    @Column(name = "Email", nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "Password", nullable = false, length = 30)
    private String password;

    @Column(name = "PhoneNumber", nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(name = "Balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
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

    public void setId(int id) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public Country getCountry() {
        return country;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }
}
