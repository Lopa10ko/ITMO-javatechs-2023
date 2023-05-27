package ru.lopa10ko.owners.client;

import java.util.UUID;

import ru.lopa10ko.owners.client.dto.OwnerDto;

public interface OwnersClient {

    OwnerDto getSelf();
    OwnerDto adminGetById(UUID uuid);
}
