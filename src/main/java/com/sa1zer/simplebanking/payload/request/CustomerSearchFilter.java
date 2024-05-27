package com.sa1zer.simplebanking.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CustomerSearchFilter(
        @JsonFormat(pattern = "dd.MM.yyyy") LocalDate birthday,
        @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String phone,
        String name,
        @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String email,
        Integer page,
        Integer size
) {
}
