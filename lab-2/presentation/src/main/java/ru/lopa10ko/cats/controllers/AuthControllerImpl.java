package ru.lopa10ko.cats.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lopa10ko.cats.controllers.requests.JwtRequest;
import ru.lopa10ko.cats.controllers.response.JwtResponse;
import ru.lopa10ko.cats.services.JwtAuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControllerImpl {
    private final JwtAuthService jwtAuthService;
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody JwtRequest request) {
        return ResponseEntity.ok(jwtAuthService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest request) {
        return ResponseEntity.ok(jwtAuthService.authenticate(request));
    }
}
