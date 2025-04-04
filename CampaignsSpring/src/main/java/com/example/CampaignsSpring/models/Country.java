package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CountryID")
    private int id;

    @Column(name = "CountryISO2Code", length = 2, nullable = false, unique = true)
    private String countryISO2Code;

    @Column(name = "CountryName", nullable = false)
    private String countryName;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<User> users;

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryISO2Code() {
        return countryISO2Code;
    }

    public String getCountryName() {
        return countryName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setCountryISO2Code(String countryISO2Code) {
        this.countryISO2Code = countryISO2Code;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
