package ru.lopa10ko.cats.security.model;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Roles implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER");
    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
