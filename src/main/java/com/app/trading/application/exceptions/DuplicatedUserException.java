package com.app.trading.application.exceptions;

import com.app.trading.domain.exceptions.CustomException;

public class DuplicatedUserException extends CustomException {
    public DuplicatedUserException() {
    }

    public DuplicatedUserException(String message) {
        super(message);
    }

    public DuplicatedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedUserException(Throwable cause) {
        super(cause);
    }
}
