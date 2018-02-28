package com.yazaki.yazaki.domain.exception;

public class DateIllegalArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DateIllegalArgumentException() {
		super();
	}

	public DateIllegalArgumentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DateIllegalArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public DateIllegalArgumentException(String message) {
		super(message);
	}

	public DateIllegalArgumentException(Throwable cause) {
		super(cause);
	}
}
