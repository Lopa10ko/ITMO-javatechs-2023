package ru.lopa10ko.cats.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.dao.CatOwnerRepository;
import ru.lopa10ko.cats.dao.CatRepository;
import ru.lopa10ko.cats.dto.CatDto;
import ru.lopa10ko.cats.dto.CatOwnerDto;
import ru.lopa10ko.cats.entities.Cat;
import ru.lopa10ko.cats.entities.CatOwner;
import ru.lopa10ko.cats.extensions.CatExtension;
import ru.lopa10ko.cats.extensions.CatOwnerExtension;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@ExtensionMethod({CatOwnerExtension.class, CatExtension.class})
public class CatFacadeImpl implements CatFacade {
    private final CatRepository catRepository;
    private final CatOwnerRepository catOwnerRepository;
    private final EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public CatOwnerDto createCatOwner(String name, LocalDate birthDate) {
        try {
            entityManager.getTransaction().begin();
            CatOwner catOwner = new CatOwner(name, birthDate);
            CatOwnerDto catOwnerDto = catOwnerRepository.create(catOwner).orElseThrow(RuntimeException::new).asDto();
            entityManager.getTransaction().commit();
            return catOwnerDto;
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw new PersistenceException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatOwnerDto readCatOwner(UUID catOwnerUuid) {
        return catOwnerRepository.read(catOwnerUuid).orElseThrow(RuntimeException::new).asDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCatOwner(UUID catOwnerUuid) {
        try {
            CatOwner catOwner = catOwnerRepository.read(catOwnerUuid).orElseThrow(RuntimeException::new);
            catOwner.getCats().stream().toList().stream().map(Cat::getUuid).forEach(this::deleteCat);
            entityManager.getTransaction().begin();
            catOwnerRepository.delete(catOwner);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw new PersistenceException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatDto createCat(String name, LocalDate birthDay, String breed, CatColor catColor) {
        try {
            entityManager.getTransaction().begin();
            Cat cat = new Cat(name, birthDay, breed, catColor);
            catRepository.create(cat);
            entityManager.getTransaction().commit();
            return cat.asDto();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw new PersistenceException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatDto readCat(UUID catUuid) {
        return catRepository.read(catUuid).orElseThrow(RuntimeException::new).asDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCat(UUID catUuid) {
        try {
            entityManager.getTransaction().begin();
            Cat deleteCat = catRepository.read(catUuid).orElseThrow(RuntimeException::new);
            deleteCat.getCatFriends().stream().toList().forEach(deleteCat::removeFriend);
            catRepository.delete(deleteCat);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw new PersistenceException();
        }
    }

    @Override
    public void addFriend(UUID leftCatUuid, UUID rightCatUuid) {
        try {
            entityManager.getTransaction().begin();
            Cat leftCat = catRepository.read(leftCatUuid).orElseThrow(RuntimeException::new);
            Cat rightCat = catRepository.read(rightCatUuid).orElseThrow(RuntimeException::new);
            leftCat.addFriend(rightCat);
            catRepository.update(leftCat);
            catRepository.update(rightCat);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw new PersistenceException();
        }
    }

    @Override
    public void addPet(UUID ownerUuid, UUID catUuid) {
        try {
            entityManager.getTransaction().begin();
            CatOwner catOwner = catOwnerRepository.read(ownerUuid).orElseThrow(RuntimeException::new);
            Cat cat = catRepository.read(catUuid).orElseThrow(RuntimeException::new);
            catOwner.addCat(cat);
            catRepository.update(cat);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw new PersistenceException();
        }
    }
}
