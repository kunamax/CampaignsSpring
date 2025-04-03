package com.example.CampaignsSpring.repositories;

import com.example.CampaignsSpring.models.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Integer> {
    Town findByTownName(String townName);
}
