package com.example.CampaignsSpring.services;

import com.example.CampaignsSpring.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import com.example.CampaignsSpring.dto.TransactionDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transaction -> new TransactionDTO(
                        transaction.getId(),
                        transaction.getAmount().doubleValue(),
                        transaction.getStatus().toString(),
                        transaction.getTransactionDate().toString(),
                        transaction.getUser().getId(),
                        transaction.getCampaign().getId()
                ))
                .collect(Collectors.toList());
    }
}
