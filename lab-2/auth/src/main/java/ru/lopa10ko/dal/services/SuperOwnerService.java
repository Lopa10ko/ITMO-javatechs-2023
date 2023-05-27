package ru.lopa10ko.dal.services;

import java.util.UUID;

import ru.lopa10ko.owners.client.dto.OwnerDto;

public interface SuperOwnerService {

    OwnerDto getSelf();

    OwnerDto adminGetById(UUID uuid);
}
