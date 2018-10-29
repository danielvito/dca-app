package com.ddvitos.app.dca.ws.exceptions;

public class InvalidBankSlipProvidedException extends RuntimeException {

	private static final long serialVersionUID = 96364091179185L;

	public InvalidBankSlipProvidedException(String errorMessage) {
		super(errorMessage);
	}

}
