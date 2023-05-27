package ru.lopa10ko.dal.security.model;

import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class UserDto {

    UUID uuid;
    String username;

    UUID catOwnerUuid;

    Set<Roles> roles;
}
