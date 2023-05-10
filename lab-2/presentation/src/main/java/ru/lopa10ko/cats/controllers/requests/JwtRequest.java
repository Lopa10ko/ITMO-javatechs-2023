package ru.lopa10ko.cats.controllers.requests;

import lombok.Data;

@Data
public class JwtRequest {
    private String login;
    private String password;
}
