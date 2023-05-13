package ru.lopa10ko.cats.controllers.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JwtResponse {
    String type = "Bearer";
    String token;
}
