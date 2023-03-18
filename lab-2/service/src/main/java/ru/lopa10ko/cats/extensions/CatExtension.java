package ru.lopa10ko.cats.extensions;

import lombok.experimental.UtilityClass;
import ru.lopa10ko.cats.dto.CatDto;
import ru.lopa10ko.cats.entities.Cat;

@UtilityClass
public class CatExtension {
    public static CatDto asDto(Cat cat) {
        if (cat == null) return new CatDto(null, null, null, null, null, null, null);
        return new CatDto(cat.getUuid(),
                cat.getName(),
                cat.getBirthDay(),
                cat.getBreed(),
                cat.getCatColor(),
                (cat.getCatOwner() != null) ? cat.getCatOwner().getUuid() : null,
                cat.getCatFriends().stream()
                        .map(Cat::getUuid)
                        .toList());
    }
}
