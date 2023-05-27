package ru.lopa10ko.dal.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.lopa10ko.dal.client.CatsClient;
import ru.lopa10ko.dal.client.dto.CatDto;
import ru.lopa10ko.dal.commons.CatColor;
import ru.lopa10ko.dal.controllers.response.CatDtoEnriched;
import ru.lopa10ko.messaging.dto.cats.AddFriendMessage;
import ru.lopa10ko.messaging.dto.cats.AdminAddFriendMessage;
import ru.lopa10ko.messaging.dto.cats.CreateCatMessage;
import ru.lopa10ko.messaging.dto.constants.QueueName;
import ru.lopa10ko.owners.client.OwnersClient;
import ru.lopa10ko.owners.client.dto.OwnerDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuperCatServiceImpl implements SuperCatService {
    private final RabbitTemplate rabbitTemplate;
    private final CatsClient catsClient;
    private final OwnersClient ownersClient;

    @Override
    public void create(String name, UUID ownerId, LocalDate birthDay, String breed, CatColor catColor) {
        CreateCatMessage build = CreateCatMessage.builder()
            .uuid(UUID.randomUUID())
            .name(name)
            .catOwnerUuid(ownerId)
            .birthDay(birthDay)
            .breed(breed)
            .catColor(catColor)
            .build();

        log.info("Sending message {}", build);
        rabbitTemplate.convertAndSend(QueueName.CAT_CREATE, build);
    }

    @Override
    public CatDtoEnriched getById(UUID uuid) {
        CatDto byId = catsClient.getById(uuid);
        OwnerDto self = byId.getCatOwnerUuid() != null
            ? ownersClient.getSelf()
            : null;

        return CatDtoEnriched.builder()
            .uuid(byId.getUuid())
            .name(byId.getName())
            .birthDay(byId.getBirthDay())
            .breed(byId.getBreed())
            .catColor(byId.getCatColor())
            .catFriendUuids(byId.getCatFriendUuids())
            .ownerDto(self)
            .build();
    }

    @Override
    public CatDtoEnriched adminGetById(UUID uuid) {
        CatDto byId = catsClient.adminGetById(uuid);
        OwnerDto self = ownersClient.adminGetById(byId.getCatOwnerUuid());

        return CatDtoEnriched.builder()
            .uuid(byId.getUuid())
            .name(byId.getName())
            .birthDay(byId.getBirthDay())
            .breed(byId.getBreed())
            .catColor(byId.getCatColor())
            .catFriendUuids(byId.getCatFriendUuids())
            .ownerDto(self)
            .build();
    }

    @Override
    public List<CatDto> getFilteredCats(List<String> name, List<UUID> uuids, List<LocalDate> birthDay,
                                        List<CatColor> colors, List<String> breed) {
        return catsClient.getByParams(name, uuids, birthDay, colors, breed);
    }

    @Override
    public List<CatDto> adminGetFilteredCats(List<String> name, List<UUID> uuids, List<LocalDate> birthDay,
                                             List<CatColor> colors, List<String> breed) {
        return catsClient.adminGetByParams(name, uuids, birthDay, colors, breed);
    }

    @Override
    public void addFriend(UUID catId, UUID friendId, UUID ownerId) {
        AddFriendMessage build = AddFriendMessage.builder()
            .friendId(friendId)
            .catId(catId)
            .ownerId(ownerId)
            .build();

        log.info("Sending message {}", build);
        rabbitTemplate.convertAndSend(QueueName.ADD_FRIEND, build);
    }

    @Override
    public void addFriendAdmin(UUID catId, UUID friendId) {
        var build = AdminAddFriendMessage.builder()
            .friendId(friendId)
            .catId(catId)
            .build();

        log.info("Sending message {}", build);
        rabbitTemplate.convertAndSend(QueueName.ADD_FRIEND_ADMIN, build);
    }
}
