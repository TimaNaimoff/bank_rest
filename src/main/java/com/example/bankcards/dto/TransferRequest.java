package com.example.bankcards.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequest {
    private String fromCardNumber;
    private String toCardNumber;
    private BigDecimal amount;
}