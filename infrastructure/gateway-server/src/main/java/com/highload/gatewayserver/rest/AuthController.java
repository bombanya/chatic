package com.highload.gatewayserver.rest;

import com.highload.gatewayserver.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(Authentication authentication) {
        return Mono.just(ResponseEntity.ok()
                .header("Authorization", tokenService.generateToken(authentication))
                .build());
    }
}
