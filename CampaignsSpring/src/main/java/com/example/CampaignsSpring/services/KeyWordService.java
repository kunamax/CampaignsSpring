package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.KeyWordDTO;
import com.example.CampaignsSpring.repositories.KeyWordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyWordService {
    private final KeyWordRepository keyWordRepository;

    public KeyWordService(KeyWordRepository keyWordRepository) {
        this.keyWordRepository = keyWordRepository;
    }

    public List<KeyWordDTO> getAllKeyWords() {
        return keyWordRepository.findAll().stream()
                .map(keyWord -> new KeyWordDTO(keyWord.getId(), keyWord.getKeyWord()))
                .collect(Collectors.toList());
    }
}
