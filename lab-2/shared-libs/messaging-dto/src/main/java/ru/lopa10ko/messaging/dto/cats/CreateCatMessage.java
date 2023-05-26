package ru.lopa10ko.messaging.dto.cats;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.lopa10ko.dal.commons.CatColor;

@AllArgsConstructor
@Data
@Builder
@Jacksonized
public class CreateCatMessage {
    private UUID uuid;
    private String name;
    private UUID catOwnerUuid;
    private LocalDate birthDay;
    private String breed;
    private CatColor catColor;
}
