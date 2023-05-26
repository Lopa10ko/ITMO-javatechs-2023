package ru.lopa10ko.dal;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lopa10ko.dal.client.dto.CatDto;
import ru.lopa10ko.dal.commons.CatColor;
import ru.lopa10ko.dal.service.CatCheckUserService;

@RequestMapping("/api/admin/cats")
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class CatAdminController {

    private final CatCheckUserService catCheckUserService;

    @GetMapping("/{id}")
    public CatDto readCat(@PathVariable("id") UUID catUuid) {
        return catCheckUserService.adminReadCat(catUuid);
    }


    @GetMapping
    public List<CatDto> getFilteredCats(@RequestParam(defaultValue = "") List<String> name,
                                        @RequestParam(defaultValue = "") List<UUID> uuid,
                                        @RequestParam(defaultValue = "") List<LocalDate> birthDay,
                                        @RequestParam(defaultValue = "") List<CatColor> color,
                                        @RequestParam(defaultValue = "") List<String> breed) {
        return catCheckUserService.adminGetByParamsCheckUser(name, uuid, birthDay, color, breed);
    }
}

