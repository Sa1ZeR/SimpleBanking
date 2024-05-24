package com.sa1zer.simplebanking.service.jwt;

import com.sa1zer.simplebanking.entity.Customer;
import com.sa1zer.simplebanking.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.Jwts.SIG.HS512;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String TOKEN_PREFIX = "Bearer_";


    private final UserDetailsServiceImpl userDetailsService;
    @Value("${jwt.duration}")
    private long durationToken;
    private Key secretKey;

    public String generateToken(Customer customer) {
        Date now = new Date(System.currentTimeMillis());
        Date expiredTime = new Date(now.getTime() + durationToken * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id",  customer.getId());
        claims.put("email",  customer.getEmail());

        return TOKEN_PREFIX + Jwts.builder()
                .issuedAt(now)
                .expiration(expiredTime)
                .claims(claims)
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> jwtClaims = parseToken(token);

            if(jwtClaims.getPayload().getExpiration().before(new Date())) {
                return false;
            }
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails user = userDetailsService.loadUserByUsername(getUserEmail(token));

        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }


    public String getUserEmail(String token) {
        Jws<Claims> jwtClaims = parseToken(token);

        return (String) jwtClaims.getPayload().get("email");
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey())
                .build().parseSignedClaims(token);
    }

    private Key getSignInKey() {
        if(secretKey == null)
            secretKey = HS512.key().build();

        return secretKey;
    }
}
