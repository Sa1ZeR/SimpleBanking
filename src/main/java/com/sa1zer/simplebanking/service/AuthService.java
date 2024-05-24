package com.sa1zer.simplebanking.service;

import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.payload.response.AuthResponse;
import com.sa1zer.simplebanking.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public AuthResponse doAuth(String login, String password) {
        Customer customer = customerService.findByLoginOrEmail(login, login);
        if(!bCryptPasswordEncoder.matches(password, customer.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is not correct");

        return new AuthResponse(jwtService.generateToken(customer));
    }
}
