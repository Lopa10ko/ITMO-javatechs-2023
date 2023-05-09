package ru.lopa10ko.cats.security.requests;

import lombok.Data;

@Data
public class JwtRefreshRequest {
    private String tokenRefresh;
}
