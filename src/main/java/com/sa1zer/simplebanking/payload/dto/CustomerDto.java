package com.sa1zer.simplebanking.payload.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CustomerDto(
        Long id,
        String firstname,
        String lastname,
        String surname,
        String login,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy") LocalDate birthday,
        List<String> emails,
        List<String> phones,
        BankAccountDto account
) {
}
