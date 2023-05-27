package ru.lopa10ko.dal.services;


import ru.lopa10ko.dal.controllers.requests.JwtCreateCatOwnerRequest;
import ru.lopa10ko.dal.controllers.requests.JwtRequest;
import ru.lopa10ko.dal.controllers.response.JwtResponse;

public interface JwtAuthService {
    JwtResponse authenticate(JwtRequest jwtRequest);
    JwtResponse register(JwtCreateCatOwnerRequest jwtCreateCatOwnerRequest);
}
