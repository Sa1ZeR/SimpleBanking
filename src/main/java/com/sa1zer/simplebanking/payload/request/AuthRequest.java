package com.sa1zer.simplebanking.payload.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "Login or email can't be empty") String login,
        @NotBlank(message = "Password can't be empty") String password
) {
}
