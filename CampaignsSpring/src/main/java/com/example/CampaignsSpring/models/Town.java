package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TownID")
    private int id;

    @Column(name = "TownName", nullable = false)
    private String townName;

    @ManyToOne
    @JoinColumn(name = "CountryID", nullable = false)
    private Country country;

    public int getId() {
        return id;
    }

    public String getTownName() {
        return townName;
    }

    public Country getCountry() {
        return country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
