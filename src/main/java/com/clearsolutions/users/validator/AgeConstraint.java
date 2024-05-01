package com.clearsolutions.users.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeConstraint {
    String message() default "Age less then allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
