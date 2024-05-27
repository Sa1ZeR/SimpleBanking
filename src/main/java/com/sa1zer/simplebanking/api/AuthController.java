package com.sa1zer.simplebanking.api;

import com.sa1zer.simplebanking.payload.request.AuthRequest;
import com.sa1zer.simplebanking.payload.response.AuthResponse;
import com.sa1zer.simplebanking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(description = "Авторизация пользователя")
    @ApiResponse(description = "jwt токен, который необходим для дальнейших действий в системе")
    @PostMapping("signin")
    public AuthResponse auth(@Valid @RequestBody @ParameterObject AuthRequest request) {
        return authService.doAuth(request.login(), request.password());
    }
}
