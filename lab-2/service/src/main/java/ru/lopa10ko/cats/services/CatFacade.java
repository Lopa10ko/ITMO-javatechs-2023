package ru.lopa10ko.cats.services;

import java.util.UUID;

public interface CatFacade extends CatService, CatOwnerService {
    void addFriend(UUID leftCatUuid, UUID rightCatUuid);
    void addPet(UUID ownerUuid, UUID catUuid);
}