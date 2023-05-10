package ru.lopa10ko.cats.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.lopa10ko.cats.commons.exceptions.CreationException;
import ru.lopa10ko.cats.controllers.requests.JwtCreateCatOwnerRequest;
import ru.lopa10ko.cats.controllers.requests.JwtRequest;
import ru.lopa10ko.cats.controllers.response.JwtResponse;
import ru.lopa10ko.cats.services.JwtAuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControllerImpl {
    private final JwtAuthService jwtAuthService;
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody JwtCreateCatOwnerRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw CreationException.throwException();
        }
        return ResponseEntity.ok(jwtAuthService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest request) {
        return ResponseEntity.ok(jwtAuthService.authenticate(request));
    }
}
