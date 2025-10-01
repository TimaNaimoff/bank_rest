package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;


    @PostMapping("/{userId}")
    public ResponseEntity<CardDto> create(@PathVariable Long userId, @RequestBody CreateCardRequest req) {
        return ResponseEntity.ok(cardService.createCard(userId, req));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Page<CardDto>> getCards(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(cardService.getUserCards(userId, pageable));
    }


    @PostMapping("/block/{cardId}")
    public ResponseEntity<Void> block(@PathVariable Long cardId) {
        cardService.blockCard(cardId);
        return ResponseEntity.noContent().build();
    }
}