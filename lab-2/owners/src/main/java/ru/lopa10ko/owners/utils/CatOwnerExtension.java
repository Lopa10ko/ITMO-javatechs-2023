package ru.lopa10ko.owners.utils;

import lombok.experimental.UtilityClass;
import ru.lopa10ko.owners.client.dto.OwnerDto;
import ru.lopa10ko.owners.entity.Owner;

@UtilityClass
public class CatOwnerExtension {
    public static OwnerDto asDto(Owner catOwner) {
        if (catOwner == null) return OwnerDto.builder().build();

        return OwnerDto.builder()
            .uuid(catOwner.getUuid())
            .catIds(catOwner.getCats())
            .birthDay(catOwner.getBirthDay())
            .name(catOwner.getName())
            .build();
    }
}
