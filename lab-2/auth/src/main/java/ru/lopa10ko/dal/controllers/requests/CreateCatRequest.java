package ru.lopa10ko.dal.controllers.requests;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.lopa10ko.dal.commons.CatColor;
import ru.lopa10ko.dal.controllers.validators.annotations.StringValueConstraint;

@AllArgsConstructor
@Data
@Validated
public class CreateCatRequest {
    @StringValueConstraint
    private String name;
    @NotNull(message = "Cat birthday should not be null")
    @PastOrPresent(message = "Data should be in past or present")
    private LocalDate birthDay;
    @StringValueConstraint
    private String breed;
    private CatColor catColor;
}
