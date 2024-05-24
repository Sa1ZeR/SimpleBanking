package com.sa1zer.simplebanking.payload.response;

import lombok.Builder;

@Builder
public record BaseMessageResponse(String message, int httpStatus) {
}
