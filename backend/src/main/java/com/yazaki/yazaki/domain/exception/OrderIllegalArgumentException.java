package com.yazaki.yazaki.domain.exception;

public class OrderIllegalArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OrderIllegalArgumentException() {
        super();
    }

    public OrderIllegalArgumentException(String message) {
        super(message);
    }

    public OrderIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderIllegalArgumentException(Throwable cause) {
        super(cause);
    }

    protected OrderIllegalArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
