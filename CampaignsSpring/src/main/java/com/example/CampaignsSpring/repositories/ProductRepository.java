package com.example.CampaignsSpring.repositories;

import com.example.CampaignsSpring.models.Product;
import com.example.CampaignsSpring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByUser(User user);
}
