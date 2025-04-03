package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.exceptions.NotFound;
import com.example.CampaignsSpring.models.Campaign;
import com.example.CampaignsSpring.models.Product;
import com.example.CampaignsSpring.models.User;
import com.example.CampaignsSpring.repositories.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CampaignService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CampaignRepository campaignRepository;
    private final ProductRepository productRepository;
    private final KeyWordRepository keyWordRepository;
    private final TownRepository townRepository;
    private final CountryRepository countryRepository;

    public CampaignService(UserRepository userRepository,
                           TransactionRepository transactionRepository,
                           CampaignRepository campaignRepository,
                           ProductRepository productRepository,
                           KeyWordRepository keyWordRepository,
                           TownRepository townRepository,
                           CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.campaignRepository = campaignRepository;
        this.productRepository = productRepository;
        this.keyWordRepository = keyWordRepository;
        this.townRepository = townRepository;
        this.countryRepository = countryRepository;
    }

    public Campaign createCampaign(String campaignName, boolean status, BigDecimal bidAmount, BigDecimal remainingBudget, int radius, int productId, int userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFound("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("User not found"));

        Campaign campaign = new Campaign();
        campaign.setCampaignName(campaignName);
        campaign.setStatus(status);
        campaign.setBidAmount(bidAmount);
        campaign.setRemainingBudget(remainingBudget);
        campaign.setRadius(radius);
        campaign.setProduct(product);
        campaign.setUser(user);

        return campaignRepository.save(campaign);
    }

    public List<Campaign> getUserCampaigns(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("User not found"));
        return campaignRepository.findByUser(user);
    }

    public void deleteCampaign(int campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new NotFound("Campaign not found"));
        campaignRepository.delete(campaign);
    }

    public Campaign updateCampaign(int campaignId, String campaignName, boolean status, BigDecimal bidAmount, BigDecimal remainingBudget, int radius) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new NotFound("Campaign not found"));

        if (campaignName != null) {
            campaign.setCampaignName(campaignName);
        }
        if (status) {
            campaign.setStatus(status);
        }
        if (bidAmount != null) {
            campaign.setBidAmount(bidAmount);
        }
        if (remainingBudget != null) {
            campaign.setRemainingBudget(remainingBudget);
        }
        if (radius > 0) {
            campaign.setRadius(radius);
        }

        return campaignRepository.save(campaign);
    }
}
