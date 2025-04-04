package com.example.CampaignsSpring.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private LocalDate createdAt;

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

    @ManyToMany
    @JoinTable(
            name = "CampaignKeyWords",
            joinColumns = @JoinColumn(name = "CampaignID"),
            inverseJoinColumns = @JoinColumn(name = "KeyWordID")
    )
    private Set<KeyWord> keyWords = new HashSet<>();

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "TownID", nullable = true)
    private Town town;

    public void setId(int id) {
        this.id = id;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public int getId() {
        return id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public boolean isStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public BigDecimal getRemainingBudget() {
        return remainingBudget;
    }

    public int getRadius() {
        return radius;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public Set<KeyWord> getKeyWords() {
        return keyWords;
    }

    public List<String> getKeyWordsAsString() {
        return keyWords.stream()
                .map(KeyWord::getKeyWord)
                .collect(Collectors.toList());
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDate createdAt) {
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

    public void setKeyWords(Set<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    public void addKeyWord(KeyWord keyWord) {
        this.keyWords.add(keyWord);
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void clearKeyWords() {
        this.keyWords.clear();
    }
}
