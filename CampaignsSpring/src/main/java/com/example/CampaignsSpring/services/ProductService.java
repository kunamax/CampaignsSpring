package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.models.Product;
import com.example.CampaignsSpring.repositories.*;
import org.springframework.stereotype.Service;

import com.example.CampaignsSpring.models.User;

import java.util.List;


@Service
public class ProductService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CampaignRepository campaignRepository;
    private final ProductRepository productRepository;
    private final KeyWordRepository keyWordRepository;
    private final TownRepository townRepository;
    private final CountryRepository countryRepository;

    public ProductService(UserRepository userRepository,
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

    public void createProduct(String productName, String productDescription, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = new Product();
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setUser(user);

        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
