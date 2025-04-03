package com.example.CampaignsSpring.repositories;

import com.example.CampaignsSpring.models.Campaign;
import com.example.CampaignsSpring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    List<Campaign> findByUser(User user);
}
