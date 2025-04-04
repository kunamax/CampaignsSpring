package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int id;

    @Column(name = "ProductName", nullable = false)
    private String productName;

    @Column(name = "ProductDescription", nullable = false)
    private String productDescription;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Campaign> campaigns;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public User getUser() {
        return user;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
