package ru.lopa10ko.cats.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lopa10ko.cats.entities.Cat;
import java.util.List;
import java.util.UUID;

@Repository
public interface CatRepository extends CrudRepository<Cat, UUID>, JpaSpecificationExecutor<Cat> {
    Cat findByUuidAndCatOwnerUuid(UUID uuid, UUID catOwnerUuid);

    List<Cat> findFriendsByUuidIn(List<UUID> uuids);
    boolean existsByName(String name);
}
