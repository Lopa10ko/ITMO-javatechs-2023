package ru.lopa10ko.dal.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import ru.lopa10ko.dal.client.dto.CatDto;
import ru.lopa10ko.dal.commons.CatColor;
import ru.lopa10ko.dal.controllers.response.CatDtoEnriched;

public interface SuperCatService {
    void create(String name, UUID ownerId, LocalDate birthDay, String breed, CatColor catColor);
    CatDtoEnriched getById(UUID uuid);
    CatDtoEnriched adminGetById(UUID uuid);

    List<CatDto> getFilteredCats(
        List<String> name,
        List<UUID> uuids,
        List<LocalDate> birthDay,
        List<CatColor> colors,
        List<String> breed
    );

    List<CatDto> adminGetFilteredCats(
        List<String> name,
        List<UUID> uuids,
        List<LocalDate> birthDay,
        List<CatColor> colors,
        List<String> breed
    );

    void addFriend(UUID catId, UUID friendId, UUID ownerId);

    void addFriendAdmin(UUID catId, UUID friendId);
}
