package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.UserDTO;
import com.example.CampaignsSpring.exceptions.DuplicateException;
import com.example.CampaignsSpring.models.Country;
import com.example.CampaignsSpring.models.User;
import com.example.CampaignsSpring.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CountryRepository countryRepository;

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

    @InjectMocks
    private UserService userService;

    private User testUser;
    private Country testCountry;

    @BeforeEach
    void setUp() {
        testCountry = new Country();
        testCountry.setCountryISO2Code("US");

        testUser = new User();
        testUser.setId(1);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser.setPhoneNumber("123456789");
        testUser.setBalance(new BigDecimal("1000.00"));
        testUser.setCreatedAt(LocalDate.now());
        testUser.setCountry(testCountry);
    }

    @Test
    void createUser_Success() {
        // Given
        when(countryRepository.findByCountryISO2Code("US")).thenReturn(Optional.of(testCountry));
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.existsByPhoneNumber("123456789")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        userService.createUser(
                "Test User",
                "test@example.com",
                "password",
                "123456789",
                new BigDecimal("1000.00"),
                "US"
        );

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_DuplicateEmail() {
        // Given
        when(countryRepository.findByCountryISO2Code("US")).thenReturn(Optional.of(testCountry));
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When & Then
        assertThrows(DuplicateException.class, () -> {
            userService.createUser(
                    "Test User",
                    "test@example.com",
                    "password",
                    "123456789",
                    new BigDecimal("1000.00"),
                    "US"
            );
        });
    }

    @Test
    void createUser_DuplicatePhoneNumber() {
        // Given
        when(countryRepository.findByCountryISO2Code("US")).thenReturn(Optional.of(testCountry));
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.existsByPhoneNumber("123456789")).thenReturn(true);

        // When & Then
        assertThrows(DuplicateException.class, () -> {
            userService.createUser(
                    "Test User",
                    "test@example.com",
                    "password",
                    "123456789",
                    new BigDecimal("1000.00"),
                    "US"
            );
        });
    }

    @Test
    void createUser_CountryNotFound() {
        // Given
        when(countryRepository.findByCountryISO2Code("XX")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userService.createUser(
                    "Test User",
                    "test@example.com",
                    "password",
                    "123456789",
                    new BigDecimal("1000.00"),
                    "XX"
            );
        });
    }

    @Test
    void getAllUsers_Success() {
        // Given
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        // When
        List<UserDTO> result = userService.getAllUsers();

        // Then
        assertEquals(1, result.size());
        assertEquals("Test User", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserBalance_Success() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        // When
        double balance = userService.getUserBalance(1);

        // Then
        assertEquals(1000.00, balance);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getUserBalance_UserNotFound() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userService.getUserBalance(1);
        });
    }
}