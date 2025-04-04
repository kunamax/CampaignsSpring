package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.TownDTO;
import com.example.CampaignsSpring.models.Town;
import com.example.CampaignsSpring.repositories.TownRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TownServiceTest {

    @Mock
    private TownRepository townRepository;

    @InjectMocks
    private TownService townService;

    @Test
    void getAllTowns_shouldReturnEmptyList_whenNoTownsExist() {
        // Given
        when(townRepository.findAll()).thenReturn(List.of());

        // When
        List<TownDTO> result = townService.getAllTowns();

        // Then
        assertTrue(result.isEmpty());
        verify(townRepository, times(1)).findAll();
    }

    @Test
    void getAllTowns_shouldReturnListOfTownDTOs_whenTownsExist() {
        // Given
        Town town1 = createTestTown(1, "Warsaw");
        Town town2 = createTestTown(2, "Krakow");

        when(townRepository.findAll()).thenReturn(List.of(town1, town2));

        // When
        List<TownDTO> result = townService.getAllTowns();

        // Then
        assertEquals(2, result.size());

        TownDTO dto1 = result.get(0);
        assertEquals(1, dto1.getId());
        assertEquals("Warsaw", dto1.getTownName());

        TownDTO dto2 = result.get(1);
        assertEquals(2, dto2.getId());
        assertEquals("Krakow", dto2.getTownName());

        verify(townRepository, times(1)).findAll();
    }

    @Test
    void getAllTowns_shouldReturnCorrectDTO_whenSingleTownExists() {
        // Given
        Town town = createTestTown(1, "Gdansk");
        when(townRepository.findAll()).thenReturn(List.of(town));

        // When
        List<TownDTO> result = townService.getAllTowns();

        // Then
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Gdansk", result.get(0).getTownName());
        verify(townRepository, times(1)).findAll();
    }

    @Test
    void getAllTowns_shouldCallRepositoryExactlyOnce() {
        // Given
        when(townRepository.findAll()).thenReturn(List.of());

        // When
        townService.getAllTowns();

        // Then
        verify(townRepository, times(1)).findAll();
        verifyNoMoreInteractions(townRepository);
    }

    private Town createTestTown(int id, String name) {
        Town town = new Town();
        town.setId(id);
        town.setTownName(name);
        return town;
    }
}