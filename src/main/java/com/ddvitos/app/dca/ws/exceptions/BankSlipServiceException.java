package com.ddvitos.app.dca.ws.exceptions;

public class BankSlipServiceException extends RuntimeException {

	private static final long serialVersionUID = 2833879146851716638L;

	public BankSlipServiceException(String errorMessage) {
		super(errorMessage);
	}

}
