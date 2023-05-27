package ru.lopa10ko.dal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import ru.lopa10ko.dal.client.dto.CatDto;
import ru.lopa10ko.dal.commons.CatColor;
import ru.lopa10ko.messaging.dto.cats.AddFriendMessage;
import ru.lopa10ko.messaging.dto.cats.AdminAddFriendMessage;
import ru.lopa10ko.messaging.dto.cats.CreateCatMessage;

public interface CatCheckUserService {
    void createCatCheckUser(CreateCatMessage createCatMessage);

    /**
     * Double DAO reading method
     * @param catUuid id to read
     * @return CatDto
     * @see CatDto
     */
    CatDto readCatCheckUser(UUID catUuid);

    CatDto adminReadCat(UUID catUuid);

    void addFriend(AddFriendMessage message);
    void addFriendAdmin(AdminAddFriendMessage message);

    List<CatDto> getByParamsCheckUser(List<String> name, List<UUID> uuid, List<LocalDate> birthDay, List<CatColor> color, List<String> breed);

    List<CatDto> adminGetByParamsCheckUser(List<String> name, List<UUID> uuid, List<LocalDate> birthDay, List<CatColor> color, List<String> breed);

}
