package com.sa1zer.simplebanking.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CustomerSearchFilter(
        @Parameter(description = "Дата рождения клиента") @JsonFormat(pattern = "dd.MM.yyyy") LocalDate birthday,
        @Parameter(description = "Номер телефона клиента")  @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String phone,
        @Parameter(description = "ФИО клиента") String name,
        @Parameter(description = "Email клиента") @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String email,
        @Parameter(description = "Номер страницы") Integer page,
        @Parameter(description = "Кол-во записей на страницу") Integer size
) {
}
