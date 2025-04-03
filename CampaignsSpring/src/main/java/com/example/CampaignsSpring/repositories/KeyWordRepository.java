package com.example.CampaignsSpring.repositories;

import com.example.CampaignsSpring.models.KeyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyWordRepository extends JpaRepository<KeyWord, Integer> {
}
