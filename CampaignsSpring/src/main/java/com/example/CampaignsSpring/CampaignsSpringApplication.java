package com.example.CampaignsSpring;

import com.example.CampaignsSpring.initializers.LoadCountries;
import com.example.CampaignsSpring.repositories.CountryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CampaignsSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampaignsSpringApplication.class, args);
	}

}
