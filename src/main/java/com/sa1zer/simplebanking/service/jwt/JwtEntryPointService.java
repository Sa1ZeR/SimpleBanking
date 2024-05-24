package com.sa1zer.simplebanking.service.jwt;

import com.google.gson.Gson;
import com.sa1zer.simplebanking.payload.response.BaseMessageResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtEntryPointService implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String responseJson;
        if(e instanceof BadCredentialsException) {
            responseJson = new Gson().toJson(BaseMessageResponse.builder()
                    .message("Неверный логин или пароль")
                    .httpStatus(403)
                    .build());
        } else {
            responseJson = new Gson().toJson(BaseMessageResponse.builder()
                    .message("Доступ запрещен")
                    .httpStatus(403)
                    .build());
        }

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().println(responseJson);
    }
}
