package ru.lopa10ko.cats.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.commons.exceptions.CatNotFoundException;
import ru.lopa10ko.cats.commons.exceptions.CatOwnerNotFoundException;
import ru.lopa10ko.cats.dao.CatOwnerRepository;
import ru.lopa10ko.cats.dao.CatRepository;
import ru.lopa10ko.cats.dto.CatDto;
import ru.lopa10ko.cats.dto.CatOwnerDto;
import ru.lopa10ko.cats.entities.Cat;
import ru.lopa10ko.cats.entities.CatOwner;
import ru.lopa10ko.cats.entities.Cat_;
import ru.lopa10ko.cats.extensions.CatExtension;
import ru.lopa10ko.cats.extensions.CatOwnerExtension;
import ru.lopa10ko.cats.security.model.CatsAuthentication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
        return getCatOwnerByUuid(catOwnerUuid).asDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCatOwner(UUID catOwnerUuid) {
        CatOwner catOwner = getCatOwnerByUuid(catOwnerUuid);
        catOwner.getCats().stream().toList().stream().map(Cat::getUuid).forEach(this::deleteCat);
        catOwnerRepository.delete(catOwner);
    }

    @SuppressWarnings("unchecked")
    @Override
    public CatOwnerDto getCurrentOwner() {
        //TODO: move to security module (getUuid)
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(CatsAuthentication.class::cast)
                .map(CatsAuthentication::getUuid)
                .flatMap(catOwnerRepository::findById)
                .map(CatOwnerExtension::asDto)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatDto createCat(String name, UUID catOwnerUuid, LocalDate birthDay, String breed, CatColor catColor) {
        Cat cat = catRepository.save(new Cat(name, birthDay, breed, catColor));
        addPet(catOwnerUuid, cat.getUuid());
        return cat.asDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatDto readCat(UUID catUuid) {
        return getCatByUuid(catUuid).asDto();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCat(UUID catUuid) {
        Cat deleteCat = getCatByUuid(catUuid);
        deleteCat.getCatFriends().stream().toList().forEach(deleteCat::removeFriend);
        catRepository.delete(deleteCat);
    }

    @Override
    public List<CatDto> getByParams(List<String> name, List<UUID> uuid, List<LocalDate> birthDay, List<CatColor> color, List<String> breed) {
        return catRepository.findAll((Specification.where((Specification<Cat>) (root, query, criteriaBuilder) -> root.get(Cat_.uuid).in(uuid))
                        .or(Specification.where((Specification<Cat>) (root, query, criteriaBuilder) -> root.get(Cat_.name).in(name)))
                        .or(Specification.where((Specification<Cat>) (root, query, criteriaBuilder) -> root.get(Cat_.birthDay).in(birthDay)))
                        .or(Specification.where((Specification<Cat>) (root, query, criteriaBuilder) -> root.get(Cat_.catColor).in(color)))
                        .or(Specification.where((Specification<Cat>) (root, query, criteriaBuilder) -> root.get(Cat_.breed).in(breed)))))
                .stream()
                .map(cat -> cat.asDto())
                .toList();
    }

    @Override
    public void addFriend(UUID leftCatUuid, UUID rightCatUuid) {
        Cat leftCat = getCatByUuid(leftCatUuid);
        Cat rightCat = getCatByUuid(rightCatUuid);
        leftCat.addFriend(rightCat);
        catRepository.save(leftCat);
        catRepository.save(rightCat);
    }

    @Override
    public void addFriendCheckUser(UUID leftCatUuid, UUID rightCatUuid) {
        if (getCurrentOwner().getCats()
                .stream()
                .map(CatDto::getUuid)
                .toList()
                .contains(leftCatUuid)) {
            throw CatNotFoundException.byUuid(leftCatUuid);
        }
        addFriend(leftCatUuid, rightCatUuid);
    }

    @Override
    public void addPet(UUID ownerUuid, UUID catUuid) {
        CatOwner catOwner = getCatOwnerByUuid(ownerUuid);
        Cat cat = getCatByUuid(catUuid);
        catOwner.addCat(cat);
        catRepository.save(cat);
    }


    @Override
    public CatDto createCatCheckUser(String name, UUID catOwnerUuid, LocalDate birthDay, String breed, CatColor catColor) {
        if (!getCurrentOwner().getUuid().equals(catOwnerUuid)) {
            throw CatOwnerNotFoundException.byUuid(catOwnerUuid);
        }
        return createCat(name, catOwnerUuid, birthDay, breed, catColor);
    }

    @Override
    public CatDto readCatCheckUser(UUID catUuid) {
        if (getCurrentOwner().getCats()
                .stream()
                .map(CatDto::getUuid)
                .toList()
                .contains(catUuid)) {
            throw CatNotFoundException.byUuid(catUuid);
        }
        return readCat(catUuid);
    }

    @Override
    public void deleteCatCheckUser(UUID catUuid) {
        if (getCurrentOwner().getCats()
                .stream()
                .map(CatDto::getUuid)
                .toList()
                .contains(catUuid)) {
            throw CatNotFoundException.byUuid(catUuid);
        }
        deleteCat(catUuid);
    }

    @Override
    public List<CatDto> getByParamsCheckUser(List<String> name, List<UUID> uuid, List<LocalDate> birthDay, List<CatColor> color, List<String> breed) {
        return getByParams(name, uuid, birthDay, color, breed)
                .stream()
                .filter(catDto -> catDto.getCatOwnerUuid().equals(getCurrentOwner().getUuid()))
                .toList();
    }

    private Cat getCatByUuid(UUID catUuid) {
        return catRepository.findById(catUuid).orElseThrow(() -> {throw CatNotFoundException.byUuid(catUuid);});
    }

    private CatOwner getCatOwnerByUuid(UUID ownerUuid) {
        return catOwnerRepository.findById(ownerUuid).orElseThrow(() -> {throw CatOwnerNotFoundException.byUuid(ownerUuid);});
    }
}
