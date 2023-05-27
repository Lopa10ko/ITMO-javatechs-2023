package ru.lopa10ko.dal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.lopa10ko.dal.client.dto.CatDto;
import ru.lopa10ko.dal.commons.CatColor;
import ru.lopa10ko.dal.entity.Cat;
import ru.lopa10ko.dal.repository.CatRepository;
import ru.lopa10ko.dal.security.service.SecurityDataProvider;
import ru.lopa10ko.dal.util.CatExtension;
import ru.lopa10ko.messaging.dto.cats.AddFriendMessage;
import ru.lopa10ko.messaging.dto.cats.AdminAddFriendMessage;
import ru.lopa10ko.messaging.dto.cats.CreateCatMessage;
import ru.lopa10ko.messaging.dto.constants.QueueName;

@Slf4j
@Component
@RequiredArgsConstructor
@ExtensionMethod(CatExtension.class)
public class CatCheckUserServiceImpl implements CatCheckUserService {

    private final CatRepository catRepository;
    private final SecurityDataProvider securityDataProvider;

    @Override
    @RabbitListener(queues = QueueName.CAT_CREATE)
    public void createCatCheckUser(CreateCatMessage createCatMessage) {
        log.info("Received message {}", createCatMessage);
        catRepository.save(
            Cat.builder()
                .uuid(createCatMessage.getUuid())
                .name(createCatMessage.getName())
                .ownerId(createCatMessage.getCatOwnerUuid())
                .birthDay(createCatMessage.getBirthDay())
                .breed(createCatMessage.getBreed())
                .catColor(createCatMessage.getCatColor())
                .build()
        );
    }

    @Override
    public CatDto readCatCheckUser(UUID catUuid) {
        Cat byUuidAndOwnerId = catRepository.findByUuidAndOwnerId(catUuid, securityDataProvider.getOwnerId());

        log.info("Found cat by id {}: {}", catUuid, byUuidAndOwnerId);

        return byUuidAndOwnerId.asDto();
    }

    @Override
    public CatDto adminReadCat(UUID catUuid) {
        return catRepository.findById(catUuid)
            .map(CatExtension::asDto)
            .orElseThrow(RuntimeException::new);
    }

    @Override
    @RabbitListener(queues = QueueName.ADD_FRIEND)
    public void addFriend(AddFriendMessage message) {
        log.info("Received message {}", message);
        UUID catUuid = message.getCatId();
        UUID friendUuid = message.getFriendId();
        Cat byUuidAndOwnerId = catRepository.findByUuidAndOwnerId(catUuid, message.getOwnerId());
        Cat cat = catRepository.findById(friendUuid).orElseThrow(RuntimeException::new);

        byUuidAndOwnerId.addFriend(cat);
        catRepository.save(byUuidAndOwnerId);
        catRepository.save(cat);
    }

    @RabbitListener(queues = QueueName.ADD_FRIEND_ADMIN)
    public void addFriendAdmin(AdminAddFriendMessage message) {
        log.info("Received message {}", message);
        UUID catUuid = message.getCatId();
        UUID friendUuid = message.getFriendId();
        Cat byUuidAndOwnerId = catRepository.findById(catUuid).orElseThrow(RuntimeException::new);
        Cat cat = catRepository.findById(friendUuid).orElseThrow(RuntimeException::new);

        byUuidAndOwnerId.addFriend(cat);
        catRepository.save(byUuidAndOwnerId);
        catRepository.save(cat);
    }

    @Override
    public List<CatDto> getByParamsCheckUser(List<String> name, List<UUID> uuid, List<LocalDate> birthDay,
                                             List<CatColor> color, List<String> breed) {
        UUID ownerId = securityDataProvider.getOwnerId();

        return adminGetByParamsCheckUser(name, uuid, birthDay, color, breed)
            .stream()
            .filter(it -> it.getCatOwnerUuid().equals(ownerId))
            .toList();
    }

    @Override
    public List<CatDto> adminGetByParamsCheckUser(List<String> name, List<UUID> uuid, List<LocalDate> birthDay,
                                                  List<CatColor> color, List<String> breed) {
        return catRepository.findAll(
                Specification.where((Specification<Cat>) (root, query, criteriaBuilder) ->
                        root.get("uuid").in(uuid))
                    .or(Specification.where((Specification<Cat>) (root, query, criteriaBuilder) -> root.get
                        ("name").in(name)))
                    .or(Specification.where((Specification<Cat>) (root, query, criteriaBuilder) -> root.get
                        ("birthDay").in(birthDay)))
                    .or(Specification.where((Specification<Cat>) (root, query, criteriaBuilder) -> root.get
                        ("catColor").in(color)))
                    .or(Specification.where((Specification<Cat>) (root, query, criteriaBuilder) -> root.get
                        ("breed").in(breed))))
            .stream()
            .map(CatExtension::asDto)
            .toList();
    }
}
