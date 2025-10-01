package com.example.bankcards.service;

import com.example.bankcards.dto.TransactionDto;
import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Transaction;
import com.example.bankcards.exception.BusinessException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.mapper.TransactionMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionDto transfer(TransferRequest request) {
        Card from = cardRepository.findByCardNumber(request.getFromCardNumber())
                .orElseThrow(() -> new NotFoundException("Source card not found"));
        Card to = cardRepository.findByCardNumber(request.getToCardNumber())
                .orElseThrow(() -> new NotFoundException("Destination card not found"));

        if (from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BusinessException("Insufficient funds");
        }

        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        Transaction tx = Transaction.builder()
                .fromCard(from)
                .toCard(to)
                .amount(request.getAmount())
                .build();

        transactionRepository.save(tx);
        return transactionMapper.toDto(tx);
    }
}
