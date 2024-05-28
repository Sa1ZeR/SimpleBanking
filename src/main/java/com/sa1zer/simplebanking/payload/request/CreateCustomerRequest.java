package com.sa1zer.simplebanking.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCustomerRequest(
        @Parameter(description = "ФИО клиента", required = true) @Pattern(regexp = "^([А-Яа-яA-Za-z]+(?: [А-Яа-яA-Za-z]+)*)$") String name,
        @Parameter(description = "Дата рождения  клиента", required = true)  @NotNull @JsonFormat(pattern = "dd.MM.yyyy") LocalDate birthday,
        @Parameter(description = "Логин клиента", required = true) @NotBlank(message = "Login can't be empty") String login,
        @Parameter(description = "Пароль клиента", required = true) @Length(min = 6, message = "Password can't be less than 6 characters!") String password,
        @Parameter(description = "Телефон клиента", required = true) @NotBlank @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String phone,
        @Parameter(description = "Email клиента", required = true) @NotBlank @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$", message = "Incorrect email") String email,
        @Parameter(description = "Начальный баланс клиента", required = true) @DecimalMin(value = "10", message = "Balance is not correct") BigDecimal balance
) {
}
