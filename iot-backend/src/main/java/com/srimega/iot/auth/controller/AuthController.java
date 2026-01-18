package com.srimega.iot.auth.controller;

import com.srimega.iot.auth.dto.LoginRequest;
import com.srimega.iot.auth.dto.LoginResponse;
import com.srimega.iot.auth.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String role = auth.getAuthorities().iterator().next().getAuthority();

        String token = jwtUtil.generateToken(
                request.getUsername(),
                role
        );

        return LoginResponse.builder()
                .username(request.getUsername())
                .role(role)
                .token(token)
                .build();
    }
}
