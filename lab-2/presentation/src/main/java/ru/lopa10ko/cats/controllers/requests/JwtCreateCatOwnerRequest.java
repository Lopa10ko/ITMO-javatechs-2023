package ru.lopa10ko.cats.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.lopa10ko.cats.security.model.Roles;

import java.time.LocalDate;


@Data
@EqualsAndHashCode(callSuper = true)
public class JwtCreateCatOwnerRequest extends JwtRequest {
    private final Roles defaultRole = Roles.valueOf("USER");
    @NotNull(message = "CatOwner birthday should not be null")
    @PastOrPresent(message = "Data should be in past or present")
    private LocalDate birthDate;
}
