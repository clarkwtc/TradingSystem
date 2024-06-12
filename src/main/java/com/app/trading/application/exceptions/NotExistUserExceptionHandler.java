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
public class NotExistUserExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotExistUserException.class)
    public ResponseEntity<?> handleNotExistUserException(NotExistUserException ex, WebRequest request) {
        return handleExceptionInternal(ex, ExceptionMessage.NOT_EXIST_USER, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
