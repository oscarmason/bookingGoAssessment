package com.booking.go.assessment;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RequestControllerErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value={InvalidInputException.class})
    protected ResponseEntity<Object> handleException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = InvalidInputException.INPUT_FORMAT_EXAMPLE;
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
