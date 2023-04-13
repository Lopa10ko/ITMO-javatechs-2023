package ru.lopa10ko.cats.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lopa10ko.cats.controllers.requests.CreateCatOwnerRequest;
import ru.lopa10ko.cats.dto.CatOwnerDto;
import ru.lopa10ko.cats.services.CatFacade;
import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/cat-owners")
@RestController
public class CatOwnerControllerImpl {
    private final CatFacade catFacade;
    @PostMapping
    public CatOwnerDto createCatOwner(@Valid @RequestBody CreateCatOwnerRequest createCatOwnerRequest) {
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
