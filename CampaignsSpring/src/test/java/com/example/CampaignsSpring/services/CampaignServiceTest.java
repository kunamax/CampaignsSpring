package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.CampaignDTO;
import com.example.CampaignsSpring.exceptions.NotEnoughFunds;
import com.example.CampaignsSpring.exceptions.NotFound;
import com.example.CampaignsSpring.models.*;
import com.example.CampaignsSpring.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private KeyWordRepository keyWordRepository;

    @Mock
    private TownRepository townRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CampaignService campaignService;

    private User testUser;
    private Product testProduct;
    private Campaign testCampaign;
    private Town testTown;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setName("Test User");
        testUser.setBalance(new BigDecimal("1000.00"));

        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setProductName("Test Product");

        testTown = new Town();
        testTown.setId(1);
        testTown.setTownName("Test Town");

        testCampaign = new Campaign();
        testCampaign.setId(1);
        testCampaign.setCampaignName("Test Campaign");
        testCampaign.setStatus(true);
        testCampaign.setBidAmount(new BigDecimal("100.00"));
        testCampaign.setRemainingBudget(new BigDecimal("500.00"));
        testCampaign.setRadius(50);
        testCampaign.setUser(testUser);
        testCampaign.setProduct(testProduct);
        testCampaign.setTown(testTown);
    }

    @Test
    void createCampaign_Success() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(townRepository.findByTownName("Test Town")).thenReturn(null);
        when(campaignRepository.save(any(Campaign.class))).thenReturn(testCampaign);

        // When
        CampaignDTO result = campaignService.createCampaign(
                "Test Campaign",
                true,
                new BigDecimal("100.00"),
                new BigDecimal("500.00"),
                50,
                1,
                1,
                "Test Town"
        );

        // Then
        assertNotNull(result);
        assertEquals("Test Campaign", result.getCampaignName());
        assertEquals(new BigDecimal("100.00"), result.getBidAmount());
        verify(userRepository, times(1)).findById(1);
        verify(campaignRepository, times(1)).save(any(Campaign.class));
    }

    @Test
    void createCampaign_UserNotFound() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFound.class, () -> {
            campaignService.createCampaign(
                    "Test Campaign",
                    true,
                    new BigDecimal("100.00"),
                    new BigDecimal("500.00"),
                    50,
                    1,
                    1,
                    "Test Town"
            );
        });
    }

    @Test
    void createCampaign_NotEnoughFunds() {
        // Given
        testUser.setBalance(new BigDecimal("100.00"));
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        // When & Then
        assertThrows(NotEnoughFunds.class, () -> {
            campaignService.createCampaign(
                    "Test Campaign",
                    true,
                    new BigDecimal("100.00"),
                    new BigDecimal("500.00"),
                    50,
                    1,
                    1,
                    "Test Town"
            );
        });
    }

    @Test
    void getUserCampaigns_Success() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(campaignRepository.findByUser(testUser)).thenReturn(Collections.singletonList(testCampaign));

        // When
        List<CampaignDTO> result = campaignService.getUserCampaigns(1);

        // Then
        assertEquals(1, result.size());
        assertEquals("Test Campaign", result.get(0).getCampaignName());
    }

    @Test
    void deleteCampaign_Success() {
        // Given
        when(campaignRepository.findById(1)).thenReturn(Optional.of(testCampaign));

        // When
        campaignService.deleteCampaign(1);

        // Then
        verify(campaignRepository, times(1)).delete(testCampaign);
    }

    @Test
    void updateCampaign_Success() {
        // Given
        when(campaignRepository.findById(1)).thenReturn(Optional.of(testCampaign));
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(townRepository.findByTownName("Updated Town")).thenReturn(null);
        when(campaignRepository.save(any(Campaign.class))).thenReturn(testCampaign);

        // When
        Campaign result = campaignService.updateCampaign(
                1,
                "Updated Campaign",
                false,
                new BigDecimal("150.00"),
                new BigDecimal("600.00"),
                75,
                "Updated Town",
                List.of("new", "keywords")
        );

        // Then
        assertNotNull(result);
        assertEquals("Updated Campaign", result.getCampaignName());
        assertEquals(new BigDecimal("150.00"), result.getBidAmount());
    }

    @Test
    void addKeywordsToCampaign_Success() {
        // Given
        when(campaignRepository.findById(1)).thenReturn(Optional.of(testCampaign));
        when(keyWordRepository.findByKeyWord("existing")).thenReturn(new KeyWord());
        when(keyWordRepository.findByKeyWord("new")).thenReturn(null);

        // When
        campaignService.addKeywordsToCampaign(1, List.of("existing", "new"));

        // Then
        verify(keyWordRepository, times(2)).save(any(KeyWord.class));
        verify(campaignRepository, times(1)).save(testCampaign);
    }
}