package com.example.bankcards.service;


import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class TransactionServiceTest {


    @Mock private TransactionRepository transactionRepository;
    @Mock private CardRepository cardRepository;
    @InjectMocks private TransactionService transactionService;


    private Card from;
    private Card to;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        from = Card.builder().cardNumber("1111").balance(new BigDecimal("100.00")).build();
        to = Card.builder().cardNumber("2222").balance(new BigDecimal("50.00")).build();
    }


    @Test
    void transfer_success() {
        when(cardRepository.findByCardNumber("1111")).thenReturn(Optional.of(from));
        when(cardRepository.findByCardNumber("2222")).thenReturn(Optional.of(to));


        TransferRequest req = new TransferRequest("1111", "2222", new BigDecimal("30.00"));
        var result = transactionService.transfer(req);


        assertThat(result.getAmount()).isEqualByComparingTo("30.00");
        assertThat(from.getBalance()).isEqualByComparingTo("70.00");
        assertThat(to.getBalance()).isEqualByComparingTo("80.00");
        verify(transactionRepository, times(1)).save(any());
    }


    @Test
    void transfer_insufficientFunds() {
        when(cardRepository.findByCardNumber("1111")).thenReturn(Optional.of(from));
        when(cardRepository.findByCardNumber("2222")).thenReturn(Optional.of(to));


        TransferRequest req = new TransferRequest("6666", "22222", new BigDecimal("300.00"));
        assertThrows(RuntimeException.class, () -> transactionService.transfer(req));
    }
}