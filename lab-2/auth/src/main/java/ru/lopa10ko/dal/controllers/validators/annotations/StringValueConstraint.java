package ru.lopa10ko.dal.controllers.validators.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.lopa10ko.dal.controllers.validators.StringValueValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringValueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValueConstraint {
    String message() default "Invalid cat owner metadata";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
