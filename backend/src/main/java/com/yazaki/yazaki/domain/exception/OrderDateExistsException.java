package com.yazaki.yazaki.domain.exception;

public class OrderDateExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OrderDateExistsException() {
        super();
    }

    public OrderDateExistsException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OrderDateExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDateExistsException(String message) {
        super(message);
    }

    public OrderDateExistsException(Throwable cause) {
        super(cause);
    }
}
