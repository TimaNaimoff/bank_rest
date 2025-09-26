package com.example.bankcards.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "card_number", nullable = false, unique = true, length = 255)
    private String cardNumber; // хранится зашифрованным


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;


    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;


    @Column(nullable = false, length = 20)
    private String status;


    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;


    @OneToMany(mappedBy = "fromCard")
    private Set<Transaction> outgoingTransactions = new HashSet<>();


    @OneToMany(mappedBy = "toCard")
    private Set<Transaction> incomingTransactions = new HashSet<>();
}
