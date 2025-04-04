package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.dto.TransactionDTO;
import com.example.CampaignsSpring.models.*;
import com.example.CampaignsSpring.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void getAllTransactions_shouldReturnEmptyList_whenNoTransactionsExist() {
        // Given
        when(transactionRepository.findAll()).thenReturn(List.of());

        // When
        List<TransactionDTO> result = transactionService.getAllTransactions();

        // Then
        assertTrue(result.isEmpty());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void getAllTransactions_shouldReturnListOfTransactionDTOs_whenTransactionsExist() {
        // Given
        User user = new User();
        user.setId(1);

        Campaign campaign = new Campaign();
        campaign.setId(1);

        Transaction transaction1 = new Transaction();
        transaction1.setId(1);
        transaction1.setAmount(BigDecimal.valueOf(100.50));
        transaction1.setStatus(Transaction.TransactionStatus.CAMPAIGN_CREATED);
        transaction1.setTransactionDate(LocalDate.of(2023, 1, 15));
        transaction1.setUser(user);
        transaction1.setCampaign(campaign);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2);
        transaction2.setAmount(BigDecimal.valueOf(200.75));
        transaction2.setStatus(Transaction.TransactionStatus.CAMPAIGN_CREATED);
        transaction2.setTransactionDate(LocalDate.of(2023, 1, 16));
        transaction2.setUser(user);
        transaction2.setCampaign(campaign);

        when(transactionRepository.findAll()).thenReturn(List.of(transaction1, transaction2));

        // When
        List<TransactionDTO> result = transactionService.getAllTransactions();

        // Then
        assertEquals(2, result.size());

        TransactionDTO dto1 = result.get(0);
        assertEquals(1, dto1.getId());
        assertEquals(100.50, dto1.getAmount());
        assertEquals(1, dto1.getUserId());
        assertEquals(1, dto1.getCampaignId());

        TransactionDTO dto2 = result.get(1);
        assertEquals(2, dto2.getId());
        assertEquals(200.75, dto2.getAmount());
        assertEquals(1, dto2.getUserId());
        assertEquals(1, dto2.getCampaignId());

        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void getAllTransactions_shouldCorrectlyMapSingleTransaction() {
        // Given
        User user = new User();
        user.setId(1);

        Campaign campaign = new Campaign();
        campaign.setId(1);

        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setAmount(BigDecimal.valueOf(150.25));
        transaction.setStatus(Transaction.TransactionStatus.CAMPAIGN_CREATED);
        transaction.setTransactionDate(LocalDate.of(2023, 1, 20));
        transaction.setUser(user);
        transaction.setCampaign(campaign);

        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        // When
        List<TransactionDTO> result = transactionService.getAllTransactions();

        // Then
        assertEquals(1, result.size());
        TransactionDTO dto = result.get(0);
        assertEquals(1, dto.getId());
        assertEquals(150.25, dto.getAmount());
        assertEquals(LocalDate.of(2023, 1, 20).toString(), dto.getTransactionStatus());
        assertEquals(1, dto.getUserId());
        assertEquals(1, dto.getCampaignId());
    }

    @Test
    void getAllTransactions_shouldCallRepositoryExactlyOnce() {
        // Given
        when(transactionRepository.findAll()).thenReturn(List.of());

        // When
        transactionService.getAllTransactions();

        // Then
        verify(transactionRepository, times(1)).findAll();
        verifyNoMoreInteractions(transactionRepository);
    }
}