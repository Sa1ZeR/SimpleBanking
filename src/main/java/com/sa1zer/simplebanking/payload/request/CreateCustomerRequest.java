package com.sa1zer.simplebanking.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCustomerRequest(
        @NotBlank(message = "Firstname can't be empty") String firstname,
        @NotBlank(message = "Lastname can't be empty") String lastname,
        String surname,
        @NotNull @JsonFormat(pattern = "dd.MM.yyyy") LocalDate birthday,
        @NotBlank(message = "Login can't be empty") String login,
        @Length(min = 6, message = "Password can't be less than 6 characters!") String password,
        @NotBlank @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String phone,
        @NotBlank @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$", message = "Incorrect email") String email,
        @DecimalMin(value = "10", message = "Balance is not correct") BigDecimal balance
) {
}
