package ru.lopa10ko.dal.client;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import ru.lopa10ko.dal.client.dto.CatDto;
import ru.lopa10ko.dal.commons.CatColor;

public interface CatsClient {

    CatDto getById(UUID uuid);
    CatDto adminGetById(UUID uuid);

    List<CatDto> getByParams(List<String> name, List<UUID> uuid, List<LocalDate> birthDay, List<CatColor> color, List<String> breed);
    List<CatDto> adminGetByParams(List<String> name, List<UUID> uuid, List<LocalDate> birthDay, List<CatColor> color, List<String> breed);
}
