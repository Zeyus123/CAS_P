package com.aashdit.prod.cmc.framework.core.exception;

public class CommonException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public CommonException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public CommonException() {
		super();
	}

}
