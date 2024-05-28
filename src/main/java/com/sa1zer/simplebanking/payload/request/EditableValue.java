package com.sa1zer.simplebanking.payload.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Pattern;

public record EditableValue(
        @Parameter(description = "Старое значение") String oldValue,
        @Parameter(description = "Новое значение") @Pattern(regexp = "[0-9]{11}", message = "Incorrect phone number") String newValue
) {
}
