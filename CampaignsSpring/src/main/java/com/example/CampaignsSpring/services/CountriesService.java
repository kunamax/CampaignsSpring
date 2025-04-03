package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.models.Country;
import com.example.CampaignsSpring.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountriesService {
    private final CountryRepository countryRepository;

    public CountriesService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // TODO : Country leaks user data
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

}
