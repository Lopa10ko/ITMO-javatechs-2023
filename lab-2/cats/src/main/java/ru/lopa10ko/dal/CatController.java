package ru.lopa10ko.dal;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lopa10ko.dal.client.dto.CatDto;
import ru.lopa10ko.dal.commons.CatColor;
import ru.lopa10ko.dal.service.CatCheckUserService;

@AllArgsConstructor
@RequestMapping("/api/cats")
@RestController
@PreAuthorize("hasAuthority('USER')")
@SecurityRequirement(name = "Bearer Authentication")
public class CatController {

    private final CatCheckUserService catCheckUserService;

    @GetMapping("/{id}")
    public CatDto readCat(@PathVariable("id") UUID catUuid) {
        return catCheckUserService.readCatCheckUser(catUuid);
    }

    @GetMapping
    public List<CatDto> getFilteredCats(@RequestParam(defaultValue = "") List<String> name,
                                        @RequestParam(defaultValue = "") List<UUID> uuid,
                                        @RequestParam(defaultValue = "") List<LocalDate> birthDay,
                                        @RequestParam(defaultValue = "") List<CatColor> color,
                                        @RequestParam(defaultValue = "") List<String> breed) {
        return catCheckUserService.getByParamsCheckUser(
            name, uuid, birthDay, color, breed
        );
    }
}
