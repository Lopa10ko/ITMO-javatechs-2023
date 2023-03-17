package ru.lopa10ko.cats.controllers;

import ru.lopa10ko.cats.dto.CatOwnerDto;

import java.time.LocalDate;
import java.util.UUID;

public interface CatOwnerController {
    /**
     * Double DAO creation method
     * @param name string
     * @param birthDate LocalDate
     * @return CatOwnerDto
     * @see CatOwnerDto
     * @see ru.lopa10ko.cats.services.CatOwnerService
     */
    CatOwnerDto createCatOwner(String name, LocalDate birthDate);

    /**
     * Double Service reading method
     * @param catOwnerUuid UUID to read
     * @return CatOwnerDto
     * @see CatOwnerDto
     * @see ru.lopa10ko.cats.services.CatOwnerService
     */
    CatOwnerDto readCatOwner(UUID catOwnerUuid);

    /**
     * Double Service deletion method
     * @param catOwnerUuid id to delete
     * @see CatOwnerDto
     * @see ru.lopa10ko.cats.services.CatOwnerService
     */
    void deleteCatOwner(UUID catOwnerUuid);
}
