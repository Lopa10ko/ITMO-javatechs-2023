package ru.lopa10ko.dal.util;

import lombok.experimental.UtilityClass;
import ru.lopa10ko.dal.client.dto.CatDto;
import ru.lopa10ko.dal.entity.Cat;

@UtilityClass
public class CatExtension {
    public static CatDto asDto(Cat cat) {
        if (cat == null) return new CatDto(null, null, null, null, null, null, null);
        return new CatDto(cat.getUuid(),
            cat.getName(),
            cat.getBirthDay(),
            cat.getBreed(),
            cat.getCatColor(),
            cat.getOwnerId(),
            cat.getCatFriends().stream()
                .map(Cat::getUuid)
                .toList());
    }
}
