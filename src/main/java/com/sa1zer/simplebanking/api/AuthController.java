package com.sa1zer.simplebanking.api;

import com.sa1zer.simplebanking.payload.request.AuthRequest;
import com.sa1zer.simplebanking.payload.response.AuthResponse;
import com.sa1zer.simplebanking.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("signin")
    public AuthResponse auth(@Valid @RequestBody AuthRequest request) {
        return authService.doAuth(request.login(), request.password());
    }
}
