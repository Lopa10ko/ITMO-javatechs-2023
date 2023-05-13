package ru.lopa10ko.cats.services;

import ru.lopa10ko.cats.dto.CatOwnerDto;

import java.time.LocalDate;
import java.util.UUID;

public interface CatOwnerService {
    /**
     * Double DAO creation method
     * @param name string
     * @param birthDate LocalDate
     * @return CatOwnerDto
     * @see CatOwnerDto
     */
    CatOwnerDto createCatOwner(String name, LocalDate birthDate);

    /**
     * Double DAO reading method
     * @param catOwnerUuid UUID to read
     * @return CatOwnerDto
     * @see CatOwnerDto
     */
    CatOwnerDto readCatOwner(UUID catOwnerUuid);

    /**
     * Double DAO deletion method
     * @param catOwnerUuid id to delete
     * @see CatOwnerDto
     */
    void deleteCatOwner(UUID catOwnerUuid);

    CatOwnerDto getCurrentOwner();
}
