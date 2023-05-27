package ru.lopa10ko.messaging.dto.owner;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class OwnerCreateMessage {
    @JsonProperty("uuid")
    UUID uuid;
    @JsonProperty("name")
    String name;
    @JsonProperty("birthDay")
    LocalDate birthDay;
}
