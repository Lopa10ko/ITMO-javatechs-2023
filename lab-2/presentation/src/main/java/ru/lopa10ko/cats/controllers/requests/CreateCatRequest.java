package ru.lopa10ko.cats.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.lopa10ko.cats.commons.CatColor;
import ru.lopa10ko.cats.controllers.validators.annotations.StringValueConstraint;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Data
@Validated
public class CreateCatRequest {
    @StringValueConstraint
    private String name;
    private UUID catOwnerUuid;
    @NotNull(message = "Cat birthday should not be null")
    @PastOrPresent(message = "Data should be in past or present")
    private LocalDate birthDay;
    @StringValueConstraint
    private String breed;
    @NotNull(message = "Cat color should not be null")
    private CatColor catColor;
}
