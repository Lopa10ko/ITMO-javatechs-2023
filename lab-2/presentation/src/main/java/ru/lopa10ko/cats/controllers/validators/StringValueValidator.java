package ru.lopa10ko.cats.controllers.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.lopa10ko.cats.controllers.validators.annotations.StringValueConstraint;

public class StringValueValidator implements ConstraintValidator<StringValueConstraint, String> {

    @Override
    public void initialize(StringValueConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("[A-Za-z\s]+");
    }
}
