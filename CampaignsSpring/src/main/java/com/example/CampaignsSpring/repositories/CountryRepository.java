package com.example.CampaignsSpring.repositories;

import com.example.CampaignsSpring.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    boolean existsByCountryISO2Code(String countryISO2Code);
    Optional<Country> findByCountryISO2Code(String countryISO2Code);
}
