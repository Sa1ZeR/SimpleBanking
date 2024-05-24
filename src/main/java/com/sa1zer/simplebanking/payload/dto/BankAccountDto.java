package com.sa1zer.simplebanking.payload.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BankAccountDto(Long id, BigDecimal balance) {
}
