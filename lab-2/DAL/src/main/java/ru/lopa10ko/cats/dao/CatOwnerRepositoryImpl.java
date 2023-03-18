package ru.lopa10ko.cats.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import ru.lopa10ko.cats.entities.CatOwner;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
@RequiredArgsConstructor
public class CatOwnerRepositoryImpl implements CatOwnerRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CatOwner> create(CatOwner catOwner) {
        Objects.requireNonNull(catOwner, "CatOwner entity should not be null in create mode");
        entityManager.persist(catOwner);
        return Optional.of(catOwner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CatOwner> read(UUID uuid) {
        CatOwner catOwner = entityManager.find(CatOwner.class, uuid);
        return Optional.ofNullable(catOwner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CatOwner> update(CatOwner catOwner) {
        Objects.requireNonNull(catOwner, "CatOwner entity should not be null in update mode");
        entityManager.merge(catOwner);
        return Optional.of(catOwner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(CatOwner catOwner) {
        entityManager.remove(catOwner);
    }
}
