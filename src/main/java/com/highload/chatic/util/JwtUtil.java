package com.highload.chatic.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtKey;

    public String generateToken(Authentication authentication) {
        return "Bearer " + Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities())
                .signWith(Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Authentication parseToken(String token) {
        token = token.replaceFirst("Bearer ", "");

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        List<SimpleGrantedAuthority> grantedAuthorities = ((List<String>) claims.get("authorities"))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                grantedAuthorities
        );
    }
}
