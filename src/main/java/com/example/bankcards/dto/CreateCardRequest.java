package com.example.bankcards.dto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCardRequest {
    private LocalDate expiryDate;
    private BigDecimal initialBalance;
}
