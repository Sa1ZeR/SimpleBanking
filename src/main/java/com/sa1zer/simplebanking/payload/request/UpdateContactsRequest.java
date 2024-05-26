package com.sa1zer.simplebanking.payload.request;

import jakarta.validation.constraints.Pattern;

import java.util.Optional;

public record UpdateContactsRequest(
        @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String phone,
        @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$", message = "Incorrect email") String email
) {
}
