package com.example.bankcards.service;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;

    @Transactional
    public CardDto createCard(Long userId, CreateCardRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        Card card = Card.builder()
                .cardNumber(generateEncryptedNumber())
                .owner(user)
                .expiryDate(request.getExpiryDate())
                .status("ACTIVE")
                .balance(request.getInitialBalance() != null ? request.getInitialBalance() : BigDecimal.ZERO)
                .build();

        return cardMapper.toDto(cardRepository.save(card));
    }

    public Page<CardDto> getUserCards(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        return cardRepository.findByOwner(user, pageable).map(cardMapper::toDto);
    }

    @Transactional
    public void blockCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found with id: " + cardId));
        card.setStatus("BLOCKED");
        cardRepository.save(card);
    }

    private String generateEncryptedNumber() {
        // TODO: надо encrypting
        return "ENC-" + System.nanoTime();
    }
}

