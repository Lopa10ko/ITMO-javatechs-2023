package ru.lopa10ko.cats.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.lopa10ko.cats.controllers.validators.annotations.StringValueConstraint;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Validated
public class CreateCatOwnerRequest {
    @StringValueConstraint
    private String name;
    @NotNull(message = "CatOwner birthday should not be null")
    @PastOrPresent(message = "Data should be in past or present")
    private LocalDate birthDate;
}
