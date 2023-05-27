package ru.lopa10ko.dal.controllers.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JwtResponse {
    String type = "Bearer";
    String token;
}
