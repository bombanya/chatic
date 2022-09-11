package com.highload.chatic.rest.controller;

import com.highload.chatic.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(Authentication authentication) {
        return ResponseEntity.ok()
                .header("Authorization", tokenService.generateToken(authentication))
                .build();
    }
}
