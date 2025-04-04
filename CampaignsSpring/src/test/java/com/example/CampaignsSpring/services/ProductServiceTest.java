package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.ProductDTO;
import com.example.CampaignsSpring.models.Product;
import com.example.CampaignsSpring.models.User;
import com.example.CampaignsSpring.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private KeyWordRepository keyWordRepository;

    @Mock
    private TownRepository townRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setName("Test User");

        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setProductName("Test Product");
        testProduct.setProductDescription("Test Description");
        testProduct.setUser(testUser);
    }

    @Test
    void createProduct_Success() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        productService.createProduct("Test Product", "Test Description", 1);

        // Then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_UserNotFound() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            productService.createProduct("Test Product", "Test Description", 1);
        });
    }

    @Test
    void getUserProducts_Success() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(productRepository.findByUser(testUser)).thenReturn(List.of(testProduct));

        // When
        List<Product> result = productService.getUserProducts(1);

        // Then
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getProductName());
    }

    @Test
    void getUserProducts_UserNotFound() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            productService.getUserProducts(1);
        });
    }

    @Test
    void getAllProducts_Success() {
        // Given
        when(productRepository.findAll()).thenReturn(List.of(testProduct));

        // When
        List<ProductDTO> result = productService.getAllProducts();

        // Then
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getProductName());
        assertEquals("Test User", result.get(0).getUserName());
    }

    @Test
    void updateProduct_Success() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        productService.updateProduct(1, "Updated Product", "Updated Description");

        // Then
        verify(productRepository, times(1)).save(testProduct);
        assertEquals("Updated Product", testProduct.getProductName());
        assertEquals("Updated Description", testProduct.getProductDescription());
    }

    @Test
    void updateProduct_NotFound() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(1, "Updated Product", "Updated Description");
        });
    }

    @Test
    void deleteProduct_Success() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        doNothing().when(productRepository).delete(testProduct);

        // When
        productService.deleteProduct(1);

        // Then
        verify(productRepository, times(1)).delete(testProduct);
    }

    @Test
    void deleteProduct_NotFound() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(1);
        });
    }
}