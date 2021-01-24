package com.epam.esm.giftcertificatemodule4.rest;

import com.epam.esm.giftcertificatemodule4.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    Translator translator;

    @Autowired
    public RestExceptionHandler(Translator translator) {
        this.translator = translator;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException exception) {
        ErrorResponse error = new ErrorResponse(
                "HTTP Status: " + HttpStatus.NOT_FOUND.value(),
                "response body",
                exception.getMessage() + " " + translator.toLocale("notFound"),
                String.valueOf(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NullPointerException exception) {
        ErrorResponse error = new ErrorResponse(
                "HTTP Status: " + HttpStatus.BAD_REQUEST.value(),
                "response body",
                translator.toLocale("incorrectValue"),
                String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(AccessDeniedException exception) {
        ErrorResponse error = new ErrorResponse(
                "HTTP Status: " + HttpStatus.FORBIDDEN.value(),
                "response body",
                translator.toLocale("denied"),
                String.valueOf(HttpStatus.FORBIDDEN.value()));
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException exception) {
        ErrorResponse error = new ErrorResponse(
                "HTTP Status: " + HttpStatus.NOT_FOUND.value(),
                "response body",
                translator.toLocale("notFound"),
                String.valueOf(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse error = new ErrorResponse(
                "HTTP Status: " + HttpStatus.BAD_REQUEST.value(),
                "response body",
                translator.toLocale("errorMessage"),
                String.valueOf(HttpStatus.BAD_REQUEST.value()));
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
