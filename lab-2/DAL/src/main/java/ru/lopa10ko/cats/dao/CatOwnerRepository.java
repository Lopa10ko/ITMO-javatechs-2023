package ru.lopa10ko.cats.dao;

import ru.lopa10ko.cats.entities.CatOwner;

import java.util.Optional;
import java.util.UUID;

public interface CatOwnerRepository {
    /**
     * Create new catOwner entry
     * @param catOwner entity to persist
     * @return CatOwner
     * @see CatOwner
     */
    Optional<CatOwner> create(CatOwner catOwner);

    /**
     * Read catOwner entry by UUID(string)
     * @param uuid UUID identification by id
     * @return CatOwner
     * @see CatOwner
     */
    Optional<CatOwner> read(UUID uuid);

    /**
     * Update cat entry
     * @param catOwner entity to merge
     * @return Cat
     * @see CatOwner
     */
    Optional<CatOwner> update(CatOwner catOwner);

    /**
     * Remove cat entry
     * @param catOwner entity to delete
     * @see CatOwner
     */
    void delete(CatOwner catOwner);
}
