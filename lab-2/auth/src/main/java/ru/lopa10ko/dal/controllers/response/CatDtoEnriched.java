package ru.lopa10ko.dal.controllers.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import ru.lopa10ko.dal.commons.CatColor;
import ru.lopa10ko.owners.client.dto.OwnerDto;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Jacksonized
@Builder
public class CatDtoEnriched {
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
    private List<UUID> catFriendUuids;
    private OwnerDto ownerDto;
}
