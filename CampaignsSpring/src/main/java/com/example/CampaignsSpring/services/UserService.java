package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.UserDTO;
import com.example.CampaignsSpring.exceptions.DuplicateException;
import com.example.CampaignsSpring.models.Country;
import com.example.CampaignsSpring.models.User;
import com.example.CampaignsSpring.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CampaignRepository campaignRepository;
    private final ProductRepository productRepository;
    private final KeyWordRepository keyWordRepository;
    private final TownRepository townRepository;
    private final CountryRepository countryRepository;

    public UserService(UserRepository userRepository,
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


    public void createUser(String name, String email, String password, String phoneNumber, BigDecimal balance, String countryISO2Code) {
        Country country = countryRepository.findByCountryISO2Code(countryISO2Code)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateException("Email already exists");
        }
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateException("Phone number already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setBalance(balance);
        LocalDate currentDateTime = LocalDate.now();
        user.setCreatedAt(currentDateTime);
        user.setCountry(country);
        userRepository.save(user);

        List<User> users = userRepository.findAll();
        for (User u : users) {
            System.out.println("User ID: " + u.getId());
            System.out.println("Name: " + u.getName());
            System.out.println("Email: " + u.getEmail());
            System.out.println("Phone Number: " + u.getPhoneNumber());
            System.out.println("Balance: " + u.getBalance());
            System.out.println("Created At: " + u.getCreatedAt());
            System.out.println("Country: " + u.getCountry().getCountryISO2Code());
        }
    }

    // TODO coś nie śmiga z DTO
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getCountry().getCountryISO2Code(), user.getPhoneNumber(), user.getCreatedAt().toString(), user.getBalance().doubleValue()))
                .toList();
        return userDTOs;
    }
}
