package ru.lopa10ko.owners.service;

import java.time.LocalDate;
import java.util.UUID;

import ru.lopa10ko.owners.client.dto.OwnerDto;

public interface OwnerService {
    /**
     * Double DAO creation method
     * @param id owner uuid
     * @param name string
     * @param birthDate LocalDate
     * @return OwnerDto
     * @see OwnerDto
     */
    OwnerDto createCatOwner(UUID id, String name, LocalDate birthDate);

    /**
     * Double DAO reading method
     * @param catOwnerUuid UUID to read
     * @return OwnerDto
     * @see OwnerDto
     */
    OwnerDto readCatOwner(UUID catOwnerUuid);

    OwnerDto getCurrentOwner();

    OwnerDto getById(UUID uuid);
}

