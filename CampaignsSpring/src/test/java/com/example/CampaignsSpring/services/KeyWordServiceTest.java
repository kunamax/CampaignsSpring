package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.KeyWordDTO;
import com.example.CampaignsSpring.models.KeyWord;
import com.example.CampaignsSpring.repositories.KeyWordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeyWordServiceTest {

    @Mock
    private KeyWordRepository keyWordRepository;

    @InjectMocks
    private KeyWordService keyWordService;

    @Test
    void getAllKeyWords_shouldReturnEmptyList_whenNoKeywordsExist() {
        // Given
        when(keyWordRepository.findAll()).thenReturn(List.of());

        // When
        List<KeyWordDTO> result = keyWordService.getAllKeyWords();

        // Then
        assertTrue(result.isEmpty());
        verify(keyWordRepository, times(1)).findAll();
    }

    @Test
    void getAllKeyWords_shouldReturnListOfKeyWordDTOs_whenKeywordsExist() {
        // Given
        KeyWord keyWord1 = new KeyWord();
        keyWord1.setId(1);
        keyWord1.setKeyWord("Spring");

        KeyWord keyWord2 = new KeyWord();
        keyWord2.setId(2);
        keyWord2.setKeyWord("Java");

        List<KeyWord> mockKeyWords = Arrays.asList(keyWord1, keyWord2);
        when(keyWordRepository.findAll()).thenReturn(mockKeyWords);

        // When
        List<KeyWordDTO> result = keyWordService.getAllKeyWords();

        // Then
        assertEquals(2, result.size());

        KeyWordDTO dto1 = result.get(0);
        assertEquals(1, dto1.getId());
        assertEquals("Spring", dto1.getKeyWordName());

        KeyWordDTO dto2 = result.get(1);
        assertEquals(2, dto2.getId());
        assertEquals("Java", dto2.getKeyWordName());

        verify(keyWordRepository, times(1)).findAll();
    }

    @Test
    void getAllKeyWords_shouldReturnCorrectDTOs_whenSingleKeywordExists() {
        // Given
        KeyWord keyWord = new KeyWord();
        keyWord.setId(1);
        keyWord.setKeyWord("Test");

        when(keyWordRepository.findAll()).thenReturn(List.of(keyWord));

        // When
        List<KeyWordDTO> result = keyWordService.getAllKeyWords();

        // Then
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Test", result.get(0).getKeyWordName());
        verify(keyWordRepository, times(1)).findAll();
    }

    @Test
    void getAllKeyWords_shouldCallRepositoryExactlyOnce() {
        // Given
        when(keyWordRepository.findAll()).thenReturn(List.of());

        // When
        keyWordService.getAllKeyWords();

        // Then
        verify(keyWordRepository, times(1)).findAll();
        verifyNoMoreInteractions(keyWordRepository);
    }
}