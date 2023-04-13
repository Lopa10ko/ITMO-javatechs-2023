package ru.lopa10ko.cats.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Validated
public class CreateCatOwnerRequest {
    @NotBlank(message = "CatOwner name should not be blank")
    private String name;
    @NotNull(message = "CatOwner birthday should not be null")
    private LocalDate birthDate;
}
