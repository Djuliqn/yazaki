package com.yazaki.yazaki.domain.exception;

public class AuthorityException extends RuntimeException {

    private static final long serialVersionUID = -362542026898029774L;

    public AuthorityException() {
        super();
    }

    public AuthorityException(String message) {
        super(message);
    }

    public AuthorityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorityException(Throwable cause) {
        super(cause);
    }

    protected AuthorityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
