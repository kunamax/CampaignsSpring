package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.ProductDTO;
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
        Product savedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        System.out.println("Product created: " + savedProduct.getProductName());
        System.out.println("Product description: " + savedProduct.getProductDescription());
        System.out.println("User ID: " + savedProduct.getUser().getId());
    }

    public List<Product> getUserProducts(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return productRepository.findByUser(user);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> new ProductDTO(product.getId(), product.getProductName(), product.getProductDescription(), product.getUser().getName()))
                .toList();
        return productDTOs;
    }

    public void updateProduct(int productId, String productName, String productDescription) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductName(productName);
        product.setProductDescription(productDescription);

        productRepository.save(product);
    }

    public void deleteProduct(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }
}
