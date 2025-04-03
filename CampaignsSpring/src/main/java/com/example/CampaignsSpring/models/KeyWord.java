package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

@Entity
@Table(name = "KeyWords")
public class KeyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KeyWordID")
    private int id;

    @Column(name = "KeyWord", nullable = false)
    private String keyWord;

    @ManyToOne
    @JoinColumn(name = "CampaignID", nullable = false)
    private Campaign campaign;
}
