package ru.lopa10ko.cats.controllers;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lopa10ko.cats.dto.CatOwnerDto;
import ru.lopa10ko.cats.services.CatFacade;

@AllArgsConstructor
@RequestMapping("/api/cat-owners")
@RestController
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@SecurityRequirement(name = "Bearer Authentication")
public class CatOwnerControllerImpl {
    private final CatFacade catFacade;
    @GetMapping("/self")
    public CatOwnerDto readCatOwner() {
        return catFacade.getCurrentOwner();
    }
}
