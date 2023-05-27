package ru.lopa10ko.dal.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lopa10ko.dal.entity.Cat;

@Repository
public interface CatRepository extends CrudRepository<Cat, UUID>, JpaSpecificationExecutor<Cat> {
    Cat findByUuidAndOwnerId(UUID uuid, UUID catOwnerUuid);

    List<Cat> findFriendsByUuidIn(List<UUID> uuids);
    boolean existsByName(String name);
}
