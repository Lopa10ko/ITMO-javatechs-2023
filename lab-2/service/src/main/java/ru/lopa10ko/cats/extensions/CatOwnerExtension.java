package ru.lopa10ko.cats.extensions;

import lombok.experimental.UtilityClass;
import ru.lopa10ko.cats.dto.CatOwnerDto;
import ru.lopa10ko.cats.entities.CatOwner;

@UtilityClass
public class CatOwnerExtension {
    public static CatOwnerDto asDto(CatOwner catOwner) {
        if (catOwner == null) return new CatOwnerDto(null, null, null, null);
        return new CatOwnerDto(catOwner.getUuid(),
                catOwner.getName(),
                catOwner.getBirthDay(),
                catOwner.getCats().stream()
                        .map(CatExtension::asDto)
                        .toList());
    }
}
