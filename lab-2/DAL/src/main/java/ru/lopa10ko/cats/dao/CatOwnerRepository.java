package ru.lopa10ko.cats.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lopa10ko.cats.entities.CatOwner;
import java.util.List;
import java.util.UUID;

@Repository
public interface CatOwnerRepository extends CrudRepository<CatOwner, UUID> {
    List<CatOwner> findByName(String name);
    boolean existsByName(String name);
}
