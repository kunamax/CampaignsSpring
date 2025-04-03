package com.example.CampaignsSpring.initializers;

import com.example.CampaignsSpring.models.Country;
import com.example.CampaignsSpring.repositories.CountryRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class LoadCountries {

    @Autowired
    private CountryRepository countryRepository;

    public void loadCountries(String filePath) throws IOException {
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
        List<Country> countries = countryRepository.findAll();
        for (Country country : countries) {
            System.out.println("Country Name: " + country.getCountryName() + ", ISO Code: " + country.getCountryISO2Code());
        }
    }

//    @Override
//    public void run(String... args) throws Exception {
//        ClassPathResource resource = new ClassPathResource("countries.csv");
//        String filePath = resource.getFile().getAbsolutePath();
//        loadCountries(filePath);
//    }
}
