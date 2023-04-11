package ru.lopa10ko.cats.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Service
@Transactional
@ExtensionMethod({CatOwnerExtension.class, CatExtension.class})
public class CatFacadeImpl implements CatFacade {
    private final CatRepository catRepository;
    private final CatOwnerRepository catOwnerRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public CatOwnerDto createCatOwner(String name, LocalDate birthDate) {
        CatOwner catOwner = new CatOwner(name, birthDate);
        return catOwnerRepository.save(catOwner).asDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatOwnerDto readCatOwner(UUID catOwnerUuid) {
        return catOwnerRepository.findById(catOwnerUuid).orElseThrow(RuntimeException::new).asDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCatOwner(UUID catOwnerUuid) {
        CatOwner catOwner = catOwnerRepository.findById(catOwnerUuid).orElseThrow(RuntimeException::new);
        catOwner.getCats().stream().toList().stream().map(Cat::getUuid).forEach(this::deleteCat);
        catOwnerRepository.delete(catOwner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatDto createCat(String name, LocalDate birthDay, String breed, CatColor catColor) {
        Cat cat = new Cat(name, birthDay, breed, catColor);
        return catRepository.save(cat).asDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatDto readCat(UUID catUuid) {
        return catRepository.findById(catUuid).orElseThrow(RuntimeException::new).asDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCat(UUID catUuid) {
        Cat deleteCat = catRepository.findById(catUuid).orElseThrow(RuntimeException::new);
        deleteCat.getCatFriends().stream().toList().forEach(deleteCat::removeFriend);
        catRepository.delete(deleteCat);
    }

    @Override
    public void addFriend(UUID leftCatUuid, UUID rightCatUuid) {
        Cat leftCat = catRepository.findById(leftCatUuid).orElseThrow(RuntimeException::new);
        Cat rightCat = catRepository.findById(rightCatUuid).orElseThrow(RuntimeException::new);
        leftCat.addFriend(rightCat);
        catRepository.save(leftCat);
        catRepository.save(rightCat);
    }

    @Override
    public void addPet(UUID ownerUuid, UUID catUuid) {
        CatOwner catOwner = catOwnerRepository.findById(ownerUuid).orElseThrow(RuntimeException::new);
        Cat cat = catRepository.findById(catUuid).orElseThrow(RuntimeException::new);
        catOwner.addCat(cat);
        catRepository.save(cat);
    }
}
