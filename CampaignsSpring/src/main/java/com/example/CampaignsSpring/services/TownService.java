package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.TownDTO;
import com.example.CampaignsSpring.repositories.TownRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TownService {
    private final TownRepository townRepository;

    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    public List<TownDTO> getAllTowns() {
        return townRepository.findAll().stream()
                .map(town -> new TownDTO(town.getId(), town.getTownName()))
                .collect(Collectors.toList());
    }
}
