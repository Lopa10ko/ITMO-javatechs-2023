package ru.lopa10ko.cats.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.commons.exceptions.CreationException;
import ru.lopa10ko.cats.controllers.requests.CreateCatRequest;
import ru.lopa10ko.cats.dto.CatDto;
import ru.lopa10ko.cats.services.CatFacade;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/cats")
@RestController
public class CatControllerImpl {
    private final CatFacade catFacade;
    @PostMapping
    public CatDto createCat(@Valid @RequestBody CreateCatRequest createCatRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw CreationException.throwException();
        }
        return catFacade.createCat(createCatRequest.getName(), createCatRequest.getCatOwnerUuid(), createCatRequest.getBirthDay(), createCatRequest.getBreed(), CatColor.valueOf(createCatRequest.getCatColor()));
    }

    @GetMapping("/{id}")
    public CatDto readCat(@PathVariable("id") UUID catUuid) {
        return catFacade.readCat(catUuid);
    }
    @DeleteMapping("/{id}")
    public void deleteCat(@PathVariable("id") UUID catUuid) {
        catFacade.deleteCat(catUuid);
    }

    @GetMapping
    public List<CatDto> getFilteredCats(@RequestParam(defaultValue = "") List<String> name,
                                        @RequestParam(defaultValue = "") List<UUID> uuid,
                                        @RequestParam(defaultValue = "") List<LocalDate> birthDay,
                                        @RequestParam(defaultValue = "") List<CatColor> color,
                                        @RequestParam(defaultValue = "") List<String> breed) {
        return catFacade.getByParams(name, uuid, birthDay, color, breed);
    }
    @PatchMapping("/{id}/{friendId}")
    public void addCatFriend(@PathVariable("id") UUID catUuid, @PathVariable("friendId") UUID catFriendUuid) {
        catFacade.addFriend(catUuid, catFriendUuid);
    }
}
