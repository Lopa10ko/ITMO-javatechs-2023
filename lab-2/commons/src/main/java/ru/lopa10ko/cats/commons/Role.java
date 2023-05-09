package ru.lopa10ko.cats.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");
    @Getter
    private final String value;
}
