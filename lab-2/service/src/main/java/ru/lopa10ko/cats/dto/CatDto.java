package ru.lopa10ko.cats.dto;

import lombok.*;
import ru.lopa10ko.cats.commons.CatColor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CatDto {
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private UUID uuid;
    @Setter(AccessLevel.NONE)
    private String name;
    @Setter(AccessLevel.NONE)
    private LocalDate birthDay;
    @Setter(AccessLevel.NONE)
    private String breed;
    @Setter(AccessLevel.NONE)
    private CatColor catColor;
    @Setter(AccessLevel.NONE)
    private UUID catOwnerUuid;
    @Setter(AccessLevel.NONE)
    private List<UUID> catFriendUuids;

    public List<UUID> getCatDtoFriends() {
        return Collections.unmodifiableList(catFriendUuids);
    }
}
