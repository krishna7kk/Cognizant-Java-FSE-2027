package com.cognizant.spring_learn.controller;

import java.util.Base64;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

    @GetMapping("/authenticate")
    public String authenticate(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        String encodedCredentials = authHeader.substring("Basic ".length());

        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);

        String credentials = new String(decodedBytes);

        String[] values = credentials.split(":");

        String username = values[0];
        String password = values[1];

        System.out.println("Username : " + username);
        System.out.println("Password : " + password);

        String token = "dummy-jwt-token-for-" + username;

        return "{\"token\":\"" + token + "\"}";
    }
}