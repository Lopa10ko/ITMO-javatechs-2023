package ru.lopa10ko.cats.controllers;

import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.dto.CatDto;

import java.time.LocalDate;
import java.util.UUID;

public interface CatController {
    /**
     * Double Service creation method
     * @param name string
     * @param birthDay LocalDate
     * @param breed string
     * @param catColor CatColor
     * @return CatDto
     * @see CatDto
     */
    CatDto createCat(String name, LocalDate birthDay, String breed, CatColor catColor);

    /**
     * Double Service reading method
     * @param catUuid id to read
     * @return CatDto
     * @see CatDto
     */
    CatDto readCat(UUID catUuid);

    /**
     * Double Service deletion method
     * @param catUuid id to delete
     * @see CatDto
     */
    void deleteCat(UUID catUuid);
}
