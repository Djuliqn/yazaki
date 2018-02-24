package com.yazaki.yazaki.domain.exception;

public class DishExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DishExistsException() {
        super();
    }

    public DishExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DishExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DishExistsException(String message) {
        super(message);
    }

    public DishExistsException(Throwable cause) {
        super(cause);
    }
}
