package com.cg.trainingsite.exceptions;

public class AdminExceptions extends RuntimeException {

	public AdminExceptions() {
		super();
	}

	public AdminExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public AdminExceptions(String message, Throwable cause) {
		super(message, cause);

	}

	public AdminExceptions(String message) {
		super(message);

	}

	public AdminExceptions(Throwable cause) {
		super(cause);

	}

}
