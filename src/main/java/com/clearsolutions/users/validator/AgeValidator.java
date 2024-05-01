package com.clearsolutions.users.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<AgeConstraint, LocalDate> {
    @Value("${minUserAge}")
    int minUserAge;

    @Override
    public void initialize(AgeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate dateToValidate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate now = LocalDate.now();
        Period difference = Period.between(dateToValidate, now);
        return difference.getYears() >= minUserAge;
    }
}
