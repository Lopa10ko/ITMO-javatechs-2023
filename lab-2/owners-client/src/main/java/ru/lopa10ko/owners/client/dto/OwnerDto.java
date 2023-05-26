package ru.lopa10ko.owners.client.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class OwnerDto {
    private UUID uuid;
    private String name;
    private LocalDate birthDay;
    private Set<UUID> catIds;
}
