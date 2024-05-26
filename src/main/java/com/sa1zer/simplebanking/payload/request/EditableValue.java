package com.sa1zer.simplebanking.payload.request;

import jakarta.validation.constraints.Pattern;

public record EditableValue(
        @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String oldValue,
        @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String newValue
) {
}
