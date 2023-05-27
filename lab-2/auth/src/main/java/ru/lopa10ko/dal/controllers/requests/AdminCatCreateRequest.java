package ru.lopa10ko.dal.controllers.requests;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.lopa10ko.dal.commons.CatColor;

@AllArgsConstructor
@Data
@Builder
@Jacksonized
public class AdminCatCreateRequest {
    @NotNull
    private UUID uuid;
    @NotEmpty
    private String name;
    @NotNull
    private UUID catOwnerUuid;
    @NotNull
    private LocalDate birthDay;
    @NotEmpty
    private String breed;
    @NotNull
    private CatColor catColor;
}
