package ru.lopa10ko.cats.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lopa10ko.cats.controllers.requests.CreateCatRequest;
import ru.lopa10ko.cats.dto.CatDto;
import ru.lopa10ko.cats.services.CatFacade;

import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/cats")
@RestController
public class CatControllerImpl {
    private final CatFacade catFacade;
    @PostMapping
    public CatDto createCat(@Valid @RequestBody CreateCatRequest createCatRequest) {
        return catFacade.createCat(createCatRequest.getName(), createCatRequest.getCatOwnerUuid(), createCatRequest.getBirthDay(), createCatRequest.getBreed(), createCatRequest.getCatColor());
    }

    @GetMapping("/{id}")
    public CatDto readCat(@PathVariable("id") UUID catUuid) {
        return catFacade.readCat(catUuid);
    }
    @DeleteMapping("/{id}")
    public void deleteCat(@PathVariable("id") UUID catUuid) {
        catFacade.deleteCat(catUuid);
    }
}
