package ru.lopa10ko.cats.dao;

import ru.lopa10ko.cats.entities.Cat;

import java.util.Optional;
import java.util.UUID;

public interface CatRepository {
    /**
     * Create new cat entry
     * @param cat entity to persist
     * @return Cat
     * @see Cat
     */
    Optional<Cat> create(Cat cat);

    /**
     * Read cat entry by UUID(string)
     * @param uuid UUID identification by id
     * @return Cat
     * @see Cat
     */
    Optional<Cat> read(UUID uuid);

    /**
     * Update cat entry
     * @param cat Cat entity to merge
     * @return Cat
     * @see Cat
     */
    Optional<Cat> update(Cat cat);

    /**
     * Remove cat entry
     * @param cat entity to delete
     * @see Cat
     */
    void delete(Cat cat);
}
