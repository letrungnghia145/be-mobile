package com.nghiale.api.exception;

public class ConfirmationCodeError extends RuntimeException {
	private static final long serialVersionUID = 2257227728779717557L;

	public ConfirmationCodeError(String message) {
		super(message);
	}

}
