package com.sa1zer.simplebanking.payload.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull @Parameter(description = "Число, которое нужно перевести", required = true) @DecimalMin(value = "10") BigDecimal value,
        @NotNull @Parameter(description = "Логин клиента, которому осуществляется перевод", required = true) String login) {
}
