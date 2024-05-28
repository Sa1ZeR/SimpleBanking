package com.sa1zer.simplebanking.payload.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Pattern;

import java.util.Optional;

public record UpdateContactsRequest(
        @Parameter(description = "Телефон клиента") @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String phone,
        @Parameter(description = "Email клиента") @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$", message = "Incorrect email") String email
) {
}
