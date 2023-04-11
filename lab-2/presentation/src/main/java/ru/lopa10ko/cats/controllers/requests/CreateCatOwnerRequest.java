package ru.lopa10ko.cats.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class CreateCatOwnerRequest {
    private String name;
    private LocalDate birthDate;
}
