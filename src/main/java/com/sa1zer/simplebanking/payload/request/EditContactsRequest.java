package com.sa1zer.simplebanking.payload.request;

import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;

public record EditContactsRequest(
        @Parameter(description = "Телефон клиента") EditableValue phone,
        @Parameter(description = "Email клиента") EditableValue email) {
}
