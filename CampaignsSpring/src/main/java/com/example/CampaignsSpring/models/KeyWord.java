package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "KeyWords")
public class KeyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KeyWordID")
    private int id;

    @Column(name = "KeyWord", nullable = false)
    private String keyWord;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "CampaignKeyWords",
            joinColumns = @JoinColumn(name = "KeyWordID"),
            inverseJoinColumns = @JoinColumn(name = "CampaignID")
    )
    private Set<Campaign> campaigns = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setCampaign(Set<Campaign> campaign) {
        this.campaigns = campaign;
    }

    public void addCampaign(Campaign campaign) {
        this.campaigns.add(campaign);
    }
}
