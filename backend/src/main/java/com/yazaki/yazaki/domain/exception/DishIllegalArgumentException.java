package com.yazaki.yazaki.domain.exception;

public class DishIllegalArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DishIllegalArgumentException() {
		super();
	}

	public DishIllegalArgumentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DishIllegalArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public DishIllegalArgumentException(String message) {
		super(message);
	}

	public DishIllegalArgumentException(Throwable cause) {
		super(cause);
	}
}
