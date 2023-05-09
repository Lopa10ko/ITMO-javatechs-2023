package ru.lopa10ko.cats.dao;

import org.springframework.data.repository.CrudRepository;
import ru.lopa10ko.cats.entities.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
}
