package ru.lopa10ko.dal.controllers.validators.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.lopa10ko.dal.controllers.validators.ValueOfEnumValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValueOfEnumValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueOfEnumConstraint {
    Class<? extends Enum<?>> enumClass();
    String message() default "must be any of enum {enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
