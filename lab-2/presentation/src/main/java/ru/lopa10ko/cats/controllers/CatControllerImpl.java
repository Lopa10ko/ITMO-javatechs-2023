package ru.lopa10ko.cats.controllers;

import lombok.AllArgsConstructor;
import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.dto.CatDto;
import ru.lopa10ko.cats.services.CatFacade;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class CatControllerImpl implements CatController {
    private final CatFacade catFacade;


    /**
     * Double Service creation method
     *
     * @param name     string
     * @param birthDay LocalDate
     * @param breed    string
     * @param catColor CatColor
     * @return CatDto
     * @see CatDto
     */
    @Override
    public CatDto createCat(String name, LocalDate birthDay, String breed, CatColor catColor) {
        return catFacade.createCat(name, birthDay, breed, catColor);
    }

    /**
     * Double Service reading method
     *
     * @param catUuid id to read
     * @return CatDto
     * @see CatDto
     */
    @Override
    public CatDto readCat(UUID catUuid) {
        return catFacade.readCat(catUuid);
    }

    /**
     * Double Service deletion method
     *
     * @param catUuid id to delete
     * @see CatDto
     */
    @Override
    public void deleteCat(UUID catUuid) {
        catFacade.deleteCat(catUuid);
    }
}
