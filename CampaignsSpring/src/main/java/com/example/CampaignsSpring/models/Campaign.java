package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Campaigns")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CampaignID")
    private int id;

    @Column(name = "CampaignName", nullable = false)
    private String campaignName;

    @Column(name = "Status", nullable = false)
    private boolean status;

    @Column(name = "CreatedAt", nullable = false)
    private Date createdAt;

    @Column(name = "BidAmount", nullable = false)
    private BigDecimal bidAmount;

    @Column(name = "RemainingBudget", nullable = false)
    private BigDecimal remainingBudget;

    @Column(name = "Radius", nullable = false)
    private int radius;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<KeyWord> keyWords;

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public void setRemainingBudget(BigDecimal remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
