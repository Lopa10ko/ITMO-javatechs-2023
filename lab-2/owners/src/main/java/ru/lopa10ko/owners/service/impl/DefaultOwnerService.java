package ru.lopa10ko.owners.service.impl;

import java.time.LocalDate;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lopa10ko.dal.commons.exceptions.CatOwnerNotFoundException;
import ru.lopa10ko.dal.security.service.SecurityDataProvider;
import ru.lopa10ko.owners.client.dto.OwnerDto;
import ru.lopa10ko.owners.entity.Owner;
import ru.lopa10ko.owners.repository.OwnerRepository;
import ru.lopa10ko.owners.service.OwnerService;
import ru.lopa10ko.owners.utils.CatOwnerExtension;

@Slf4j
@Service
@RequiredArgsConstructor
@ExtensionMethod(CatOwnerExtension.class)
public class DefaultOwnerService implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final SecurityDataProvider securityDataProvider;

    @Override
    public OwnerDto createCatOwner(UUID id, String name, LocalDate birthDate) {
        Owner owner = Owner.builder()
            .uuid(id)
            .name(name)
            .birthDay(birthDate)
            .build();

        return ownerRepository.save(owner).asDto();
    }

    @Override
    public OwnerDto readCatOwner(UUID catOwnerUuid) {
        return getCatOwnerByUuid(catOwnerUuid)
            .asDto();
    }

    @Override
    public OwnerDto getCurrentOwner() {
        log.info("Trying to get current owner with id {}", securityDataProvider.getOwnerId());
        return getCatOwnerByUuid(securityDataProvider.getOwnerId())
            .asDto();
    }

    @Override
    public OwnerDto getById(UUID uuid) {
        return getCatOwnerByUuid(uuid).asDto();
    }

    private Owner getCatOwnerByUuid(UUID ownerUuid) {
        return ownerRepository.findById(ownerUuid).orElseThrow(() -> {throw CatOwnerNotFoundException.byUuid(ownerUuid);});
    }
}
