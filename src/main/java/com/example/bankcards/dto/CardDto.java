package com.example.bankcards.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {
    private Long id;
    private String maskedNumber;
    private LocalDate expiryDate;
    private String status;
    private BigDecimal balance;
}