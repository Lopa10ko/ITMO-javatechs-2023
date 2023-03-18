package ru.lopa10ko.cats.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import ru.lopa10ko.cats.entities.Cat;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
@RequiredArgsConstructor
public class CatRepositoryImpl implements CatRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cat> create(Cat cat) {
        Objects.requireNonNull(cat, "Cat entity should not be null in create mode");
        entityManager.persist(cat);
        return Optional.of(cat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cat> read(UUID uuid) {
        Cat cat = entityManager.find(Cat.class, uuid);
        return Optional.ofNullable(cat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Cat> update(Cat cat) {
        Objects.requireNonNull(cat, "Cat entity should not be null in update mode");
        cat = entityManager.merge(cat);
        return Optional.of(cat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Cat cat) {
        entityManager.remove(cat);
    }
}
