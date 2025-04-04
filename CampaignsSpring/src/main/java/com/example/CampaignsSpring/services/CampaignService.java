package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.CampaignDTO;
import com.example.CampaignsSpring.exceptions.NotEnoughFunds;
import com.example.CampaignsSpring.exceptions.NotFound;
import com.example.CampaignsSpring.models.*;
import com.example.CampaignsSpring.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    @Transactional
    public CampaignDTO createCampaign(String campaignName, boolean status, BigDecimal bidAmount, BigDecimal remainingBudget, int radius, int productId, int userId, String town) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFound("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("User not found"));

        if (user.getBalance().compareTo(remainingBudget) < 0) {
            throw new NotEnoughFunds("User does not have enough balance");
        }

        user.setBalance(user.getBalance().subtract(remainingBudget));
        Campaign campaign = new Campaign();
        campaign.setCampaignName(campaignName);
        campaign.setStatus(status);
        LocalDate currentDateTime = LocalDate.now();
        campaign.setCreatedAt(currentDateTime);
        campaign.setBidAmount(bidAmount);
        campaign.setRemainingBudget(remainingBudget);
        campaign.setRadius(radius);
        campaign.setProduct(product);
        campaign.setUser(user);
        Town townEntity = townRepository.findByTownName(town);
        if (townEntity == null) {
            townEntity = new Town();
            townEntity.setTownName(town);
            townEntity.addCampaign(campaign);
            townRepository.save(townEntity);
        }
        campaign.setTown(townEntity);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(remainingBudget);
        transaction.setTransactionDate(currentDateTime);
        transaction.setStatus(Transaction.TransactionStatus.CAMPAIGN_CREATED);
        campaign.addTransaction(transaction);
        transaction.setCampaign(campaign);
        Campaign result = campaignRepository.save(campaign);
        transactionRepository.save(transaction);

        List<Campaign> campaigns = campaignRepository.findAll();
        for (Campaign c : campaigns) {
            System.out.println(c.getCampaignName());
            System.out.println(c.isStatus());
            System.out.println(c.getCreatedAt());
            System.out.println(c.getBidAmount());
            System.out.println(c.getRemainingBudget());
            System.out.println(c.getRadius());
            System.out.println(c.getProduct());
            System.out.println(c.getUser());
            System.out.println(c.getKeyWords());
        }

        return new CampaignDTO(result.getId(), result.getCampaignName(), result.isStatus(), result.getBidAmount(), result.getRemainingBudget(), result.getRadius(), result.getProduct().getProductName(), result.getUser().getName(), result.getKeyWordsAsString(), result.getCreatedAt(), result.getTown().getTownName());
    }

    public List<CampaignDTO> getUserCampaigns(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFound("User not found"));
        List<CampaignDTO> campaignDTOs = campaignRepository.findByUser(user).stream()
                .map(campaign -> new CampaignDTO(campaign.getId(), campaign.getCampaignName(), campaign.isStatus(), campaign.getBidAmount(), campaign.getRemainingBudget(), campaign.getRadius(), campaign.getProduct().getProductName(), campaign.getUser().getName(), campaign.getKeyWordsAsString(), campaign.getCreatedAt(), campaign.getTown().getTownName()))
                .toList();
        return campaignDTOs;
    }

    @Transactional
    public void deleteCampaign(int campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new NotFound("Campaign not found"));
        campaign.clearKeyWords();
        campaignRepository.save(campaign);
        campaignRepository.delete(campaign);
    }

    @Transactional
    public Campaign updateCampaign(int campaignId, String campaignName, boolean status, BigDecimal bidAmount, BigDecimal remainingBudget, int radius, String town, List<String> keywords) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new NotFound("Campaign not found"));

        User user = userRepository.findById(campaign.getUser().getId())
                .orElseThrow(() -> new NotFound("User not found"));

        double balanceDiff = campaign.getRemainingBudget().doubleValue() - remainingBudget.doubleValue();
        if (user.getBalance().doubleValue() + balanceDiff < 0) {
            throw new NotEnoughFunds("User does not have enough balance");
        }
        user.setBalance(user.getBalance().add(BigDecimal.valueOf(balanceDiff)));

        campaign.setCampaignName(campaignName);
        campaign.setStatus(status);
        campaign.setBidAmount(bidAmount);
        campaign.setRemainingBudget(remainingBudget);
        campaign.setRadius(radius);
        Town townEntity = townRepository.findByTownName(town);
        if (townEntity == null) {
            townEntity = new Town();
            townEntity.setTownName(town);
            townEntity.addCampaign(campaign);
            townRepository.save(townEntity);
        }
        campaign.setTown(townEntity);
        campaign.clearKeyWords();

        return campaignRepository.save(campaign);
    }

    @Transactional
    public void addKeywordsToCampaign(int campaignId, List<String> keywords) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new NotFound("Campaign not found"));

        for (String keyword : keywords) {
            KeyWord existingKeyWord = keyWordRepository.findByKeyWord(keyword);
            if (existingKeyWord != null) {
                existingKeyWord.getCampaigns().add(campaign);
                keyWordRepository.save(existingKeyWord);
                campaign.addKeyWord(existingKeyWord);
                continue;
            }
            KeyWord keyWord = new KeyWord();
            keyWord.setKeyWord(keyword);
            keyWord.addCampaign(campaign);
            campaign.addKeyWord(keyWord);
            keyWordRepository.save(keyWord);
        }
        campaignRepository.save(campaign);
    }
}
