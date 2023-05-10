package ru.lopa10ko.cats.services;


import ru.lopa10ko.cats.controllers.requests.JwtRequest;
import ru.lopa10ko.cats.controllers.response.JwtResponse;

public interface JwtAuthService {
    JwtResponse authenticate(JwtRequest jwtRequest);
    JwtResponse register(JwtRequest jwtRequest);
}
