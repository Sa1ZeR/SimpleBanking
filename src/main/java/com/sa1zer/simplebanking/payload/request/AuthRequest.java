package com.sa1zer.simplebanking.payload.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @Parameter(description = "Логин клиента", required = true) @NotBlank(message = "Login or email can't be empty") String login,
        @Parameter(description = "Пароль клиента", required = true) @NotBlank(message = "Password can't be empty") String password
) {
}
