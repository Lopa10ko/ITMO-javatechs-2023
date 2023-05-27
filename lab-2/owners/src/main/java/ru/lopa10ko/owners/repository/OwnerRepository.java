package ru.lopa10ko.owners.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lopa10ko.owners.entity.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, UUID> {
}
