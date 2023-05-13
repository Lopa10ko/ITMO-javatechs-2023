package ru.lopa10ko.cats.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.lopa10ko.cats.commons.exceptions.CreationException;
import ru.lopa10ko.cats.controllers.requests.CreateCatOwnerRequest;
import ru.lopa10ko.cats.dto.CatOwnerDto;
import ru.lopa10ko.cats.services.CatFacade;

import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/api/admin/cat-owners")
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminCatOwnerControllerImpl {
    private final CatFacade catFacade;
    @PostMapping
    public CatOwnerDto createCatOwner(@Valid @RequestBody CreateCatOwnerRequest createCatOwnerRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw CreationException.throwException();
        }
        return catFacade.createCatOwner(createCatOwnerRequest.getName(), createCatOwnerRequest.getBirthDate());
    }
    @GetMapping("/{id}")
    public CatOwnerDto readCatOwner(@PathVariable("id") UUID catOwnerUuid) {
        return catFacade.readCatOwner(catOwnerUuid);
    }
    @DeleteMapping("/{id}")
    public void deleteCatOwner(@PathVariable("id")UUID catOwnerUuid) {
        catFacade.deleteCatOwner(catOwnerUuid);
    }
}
