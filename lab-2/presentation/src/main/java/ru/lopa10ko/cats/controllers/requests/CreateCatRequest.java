package ru.lopa10ko.cats.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.lopa10ko.cats.commons.CatColor;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Data
@Validated
public class CreateCatRequest {
    @NotBlank(message = "Cat name should not be blank")
    private String name;
    private UUID catOwnerUuid;
    @NotNull(message = "Cat birthday should not be null")
    private LocalDate birthDay;
    @NotBlank(message = "Cat breed should not be null")
    private String breed;
    @NotNull(message = "Cat color should not be null")
    private CatColor catColor;
}
