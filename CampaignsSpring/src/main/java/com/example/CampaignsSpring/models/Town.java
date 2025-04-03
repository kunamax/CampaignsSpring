package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TownID")
    private int id;

    @Column(name = "TownName", nullable = false)
    private String townName;

    @OneToMany(mappedBy = "town", cascade = CascadeType.ALL)
    private Set<Campaign> campaign = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getTownName() {
        return townName;
    }

    public Set<Campaign> getCampaign() {
        return campaign;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public void setCountry(Set<Campaign> campaign) {
        this.campaign = campaign;
    }

    public void addCampaign(Campaign campaign) {
        this.campaign.add(campaign);
    }
}
