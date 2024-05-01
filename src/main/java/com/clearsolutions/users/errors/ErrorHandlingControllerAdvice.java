package com.clearsolutions.users.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler(value = {DateTimeParseException.class})
    public ResponseEntity<Object> handleNotValidFormatFieldException(DateTimeParseException ex) {
        ValidationErrorResponse errors = new ValidationErrorResponse();
        errors.getErrors().add(new Violation(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        ValidationErrorResponse errors = new ValidationErrorResponse();
        errors.getErrors().add(new Violation(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(UserNotFoundException ex) {
        ValidationErrorResponse errors = new ValidationErrorResponse();
        errors.getErrors().add(new Violation(HttpStatus.NOT_FOUND.value(), "User not found"));
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {HandlerMethodValidationException.class})
    public ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        ValidationErrorResponse errors = new ValidationErrorResponse();
        errors.getErrors().add(new Violation(HttpStatus.BAD_REQUEST.value(),
                "Incorrect handler, cause: " + ex.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<Object> handleNotFoundException(MissingServletRequestParameterException ex) {
        ValidationErrorResponse errors = new ValidationErrorResponse();
        errors.getErrors().add(new Violation(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleNotValidFieldException(MethodArgumentNotValidException ex) {
        ValidationErrorResponse errors = new ValidationErrorResponse();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.getErrors().add(new Violation(HttpStatus.BAD_REQUEST.value(),
                    fieldError.getField() + " - " + fieldError.getDefaultMessage()));
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        ValidationErrorResponse errors = new ValidationErrorResponse();
        errors.getErrors().add(new Violation(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unhandled exception occurred, cause: " + ex.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
