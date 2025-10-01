package com.example.bankcards.mapper;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {
@Mapping(target = "maskedNumber", expression = "java(maskCardNumber(card.getCardNumber()))")
CardDto toDto(Card card);


default String maskCardNumber(String cardNumber) {
if (cardNumber == null || cardNumber.length() < 4) return "****";
return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
}
}