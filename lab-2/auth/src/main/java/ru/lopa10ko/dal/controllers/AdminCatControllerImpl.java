package ru.lopa10ko.dal.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lopa10ko.dal.client.dto.CatDto;
import ru.lopa10ko.dal.commons.CatColor;
import ru.lopa10ko.dal.controllers.requests.AdminCatCreateRequest;
import ru.lopa10ko.dal.controllers.response.CatDtoEnriched;
import ru.lopa10ko.dal.services.SuperCatService;

@RequestMapping("/api/admin/cats")
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class AdminCatControllerImpl {
    private final SuperCatService superCatService;

    @PostMapping
    public void create(@Valid @RequestBody AdminCatCreateRequest request) {
        superCatService.create(
            request.getName(),
            request.getCatOwnerUuid(),
            request.getBirthDay(),
            request.getBreed(),
            request.getCatColor()
        );
    }

    @GetMapping("/{id}")
    public CatDtoEnriched readCat(@PathVariable("id") UUID catUuid) {
        return superCatService.adminGetById(catUuid);
    }

    @GetMapping
    public List<CatDto> getFilteredCats(@RequestParam(defaultValue = "") List<String> name,
                                        @RequestParam(defaultValue = "") List<UUID> uuid,
                                        @RequestParam(defaultValue = "") List<LocalDate> birthDay,
                                        @RequestParam(defaultValue = "") List<CatColor> color,
                                        @RequestParam(defaultValue = "") List<String> breed) {
        return superCatService.adminGetFilteredCats(name, uuid, birthDay, color, breed);
    }

    @PatchMapping("/{id}/{friendId}")
    public void addCatFriend(@PathVariable("id") UUID catUuid, @PathVariable("friendId") UUID catFriendUuid) {
        superCatService.addFriendAdmin(catUuid, catFriendUuid);
    }
}
