package ru.lopa10ko.owners;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lopa10ko.owners.client.dto.OwnerDto;
import ru.lopa10ko.owners.service.OwnerService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cat-owners")
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@SecurityRequirement(name = "Bearer Authentication")
public class OwnersController {

    private final OwnerService ownerService;

    @GetMapping("/self")
    public OwnerDto readCatOwner() {
        var result =  ownerService.getCurrentOwner();

        log.info("Got owner {}", result);

        return result;
    }
}
