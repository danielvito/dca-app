package com.ddvitos.app.dca.ws.exceptions;

public class BankSlipNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8095718335678171898L;

	public BankSlipNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
