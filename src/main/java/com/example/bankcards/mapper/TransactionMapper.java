package com.example.bankcards.mapper;

import com.example.bankcards.dto.TransactionDto;
import com.example.bankcards.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
@Mapping(target = "fromCardMasked", expression = "java(maskCardNumber(transaction.getFromCard().getCardNumber()))")
@Mapping(target = "toCardMasked", expression = "java(maskCardNumber(transaction.getToCard().getCardNumber()))")
TransactionDto toDto(Transaction transaction);


default String maskCardNumber(String cardNumber) {
if (cardNumber == null || cardNumber.length() < 4) return "****";
return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
}
}