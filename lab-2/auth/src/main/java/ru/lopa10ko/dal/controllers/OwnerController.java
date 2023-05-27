package ru.lopa10ko.dal.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lopa10ko.dal.services.SuperOwnerService;
import ru.lopa10ko.owners.client.dto.OwnerDto;

@RequiredArgsConstructor
@RequestMapping("/api/owners")
@RestController
@PreAuthorize("hasAuthority('USER')")
@SecurityRequirement(name = "Bearer Authentication")
public class OwnerController {
    private final SuperOwnerService superOwnerService;


    @GetMapping("/self")
    public OwnerDto getSelf() {
        return superOwnerService.getSelf();
    }
}
