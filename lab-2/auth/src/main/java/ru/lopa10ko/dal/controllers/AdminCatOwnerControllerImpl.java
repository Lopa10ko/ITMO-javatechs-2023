package ru.lopa10ko.dal.controllers;

import java.util.UUID;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lopa10ko.dal.services.SuperOwnerService;
import ru.lopa10ko.owners.client.dto.OwnerDto;

@RequestMapping("/api/admin/cat-owners")
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class AdminCatOwnerControllerImpl {

    private final SuperOwnerService superOwnerService;
    @GetMapping("/{id}")
    public OwnerDto readCatOwner(@PathVariable("id") UUID catOwnerUuid) {
        return superOwnerService.adminGetById(catOwnerUuid);
    }
}
