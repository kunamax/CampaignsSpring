package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.models.Country;
import com.example.CampaignsSpring.repositories.CountryRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class CountriesService {
    private final CountryRepository countryRepository;

    public CountriesService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @PostConstruct
    public void loadCountries() throws IOException {
        ClassPathResource resource = new ClassPathResource("countries.csv");
        String filePath = resource.getFile().getAbsolutePath();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                String countryName = line[0].trim().toUpperCase();
                String countryISO2Code = line[1].trim().toUpperCase();

                if (!countryRepository.existsByCountryISO2Code(countryISO2Code)) {
                    Country country = new Country();
                    country.setCountryISO2Code(countryISO2Code);
                    country.setCountryName(countryName);
                    countryRepository.save(country);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV file: " + e.getMessage(), e);
        }
    }

}
