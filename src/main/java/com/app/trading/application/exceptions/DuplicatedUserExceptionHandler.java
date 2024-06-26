package com.app.trading.application.exceptions;

import com.app.trading.domain.exceptions.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DuplicatedUserExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicatedUserException.class)
    public ResponseEntity<?> handleDuplicatedUserException(DuplicatedUserException ex, WebRequest request) {
        return handleExceptionInternal(ex, ExceptionMessage.DUPLICATED_USERNAME, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
