package com.yazaki.yazaki.domain.exception;

public class YazakiException extends RuntimeException {

    private static final long serialVersionUID = 6152368717816788713L;

    public YazakiException() {
        super();
    }

    public YazakiException(String message) {
        super(message);
    }

    public YazakiException(String message, Throwable cause) {
        super(message, cause);
    }

    public YazakiException(Throwable cause) {
        super(cause);
    }

    protected YazakiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
