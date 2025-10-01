package com.example.bankcards.service;

import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @Mock private CardRepository cardRepository;
    @Mock private UserRepository userRepository;
    @Mock private CardMapper cardMapper;

    @InjectMocks private CardService cardService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().id(1L).username("john").build();
    }

    @Test
    void createCard_success() {
        CreateCardRequest req = new CreateCardRequest(LocalDate.now().plusYears(3), BigDecimal.valueOf(500));
        Card saved = Card.builder().id(1L).owner(user).balance(BigDecimal.valueOf(500)).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cardRepository.save(any())).thenReturn(saved);
        when(cardMapper.toDto(saved)).thenReturn(null); // упрощаем

        var result = cardService.createCard(1L, req);

        verify(cardRepository, times(1)).save(any());
        assertThat(result).isNull(); // тк мы вернули null из маппера
    }

    @Test
    void createCard_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> cardService.createCard(1L, new CreateCardRequest(LocalDate.now(), BigDecimal.ZERO)));
    }

    @Test
    void blockCard_success() {
        Card card = Card.builder().id(2L).status("ACTIVE").build();
        when(cardRepository.findById(2L)).thenReturn(Optional.of(card));

        cardService.blockCard(2L);

        assertThat(card.getStatus()).isEqualTo("BLOCKED");
        verify(cardRepository).save(card);
    }
}