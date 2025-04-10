package com.amrhalahla.springmicrohub.authservice.controller;

import com.amrhalahla.springmicrohub.authservice.dto.RegisterDto;
import com.amrhalahla.springmicrohub.authservice.dto.TokenDto;
import com.amrhalahla.springmicrohub.authservice.request.LoginRequest;
import com.amrhalahla.springmicrohub.authservice.request.RegisterRequest;
import com.amrhalahla.springmicrohub.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
