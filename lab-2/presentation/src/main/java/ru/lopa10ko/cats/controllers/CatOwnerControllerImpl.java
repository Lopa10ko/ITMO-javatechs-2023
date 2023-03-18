package ru.lopa10ko.cats.controllers;

import lombok.AllArgsConstructor;
import ru.lopa10ko.cats.dto.CatOwnerDto;
import ru.lopa10ko.cats.services.CatFacade;
import ru.lopa10ko.cats.services.CatOwnerService;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class CatOwnerControllerImpl implements CatOwnerController {
    private final CatFacade catFacade;

    /**
     * Double DAO creation method
     *
     * @param name      string
     * @param birthDate LocalDate
     * @return CatOwnerDto
     * @see CatOwnerDto
     * @see CatOwnerService
     */
    @Override
    public CatOwnerDto createCatOwner(String name, LocalDate birthDate) {
        return catFacade.createCatOwner(name, birthDate);
    }

    /**
     * Double Service reading method
     *
     * @param catOwnerUuid UUID to read
     * @return CatOwnerDto
     * @see CatOwnerDto
     * @see CatOwnerService
     */
    @Override
    public CatOwnerDto readCatOwner(UUID catOwnerUuid) {
        return catFacade.readCatOwner(catOwnerUuid);
    }

    /**
     * Double Service deletion method
     *
     * @param catOwnerUuid id to delete
     * @see CatOwnerDto
     * @see CatOwnerService
     */
    @Override
    public void deleteCatOwner(UUID catOwnerUuid) {
        catFacade.deleteCatOwner(catOwnerUuid);
    }
}
