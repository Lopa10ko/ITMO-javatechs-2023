package ru.lopa10ko.cats.services;

import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.dto.CatDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CatCheckUserService {
    /**
     * Double DAO creation method
     * @param name string
     * @param birthDay LocalDate
     * @param breed string
     * @param catColor CatColor
     * @return CatDto
     * @see CatDto
     */
    CatDto createCatCheckUser(String name, UUID catOwnerUuid, LocalDate birthDay, String breed, CatColor catColor);

    /**
     * Double DAO reading method
     * @param catUuid id to read
     * @return CatDto
     * @see CatDto
     */
    CatDto readCatCheckUser(UUID catUuid);

    /**
     * Double DAO deletion method
     * @param catUuid id to delete
     * @see CatDto
     */
    void deleteCatCheckUser(UUID catUuid);

    List<CatDto> getByParamsCheckUser(List<String> name, List<UUID> uuid, List<LocalDate> birthDay, List<CatColor> color, List<String> breed);
}
