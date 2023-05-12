package ru.lopa10ko.cats.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import ru.lopa10ko.cats.security.model.Roles;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JwtCreateCatOwnerRequest extends JwtRequest {
    @Getter
    @Setter(AccessLevel.NONE)
    private final Roles defaultRole = Roles.valueOf("USER");
    @Getter
    @NotNull(message = "CatOwner birthday should not be null")
    @PastOrPresent(message = "Data should be in past or present")
    private LocalDate birthDate;
}
