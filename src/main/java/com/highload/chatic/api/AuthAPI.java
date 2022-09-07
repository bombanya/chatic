package com.highload.chatic.api;

import com.highload.chatic.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthAPI {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthAPI(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(HttpServletRequest request){
        try{
            System.out.println("there");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getHeader("username"),
                            request.getHeader("password")
                    )
            );

            System.out.println("here");

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtUtil.generateToken(authentication))
                    .build();

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

    @GetMapping("test")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello, " + SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
