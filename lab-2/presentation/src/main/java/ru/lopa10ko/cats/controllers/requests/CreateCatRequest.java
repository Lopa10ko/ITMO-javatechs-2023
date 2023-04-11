package ru.lopa10ko.cats.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.lopa10ko.cats.commons.CatColor;
import java.time.LocalDate;
@AllArgsConstructor
@Data
public class CreateCatRequest {
    private String name;
    private LocalDate birthDay;
    private String breed;
    private CatColor catColor;
}
