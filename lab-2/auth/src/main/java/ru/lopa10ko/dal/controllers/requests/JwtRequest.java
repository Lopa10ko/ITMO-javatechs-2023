package ru.lopa10ko.dal.controllers.requests;

import lombok.Data;

@Data
public class JwtRequest {
    private String login;
    private String password;
}
