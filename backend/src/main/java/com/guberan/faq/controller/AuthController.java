package com.guberan.faq.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody LoginRequest loginRequest) {
        try {
            request.login(loginRequest.username(), loginRequest.password());
            return ResponseEntity.ok().build();
        } catch (ServletException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) throws ServletException {
        request.logout();
    }

    public record LoginRequest(String username, String password) {
    }
}