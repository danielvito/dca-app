package com.ddvitos.app.dca.ws.ui.model.response;

public enum ErrorMessages {

	MISSING_REQUIRED_FIELD("Invalid bankslip provided.The possible reasons are:\r\n"
			+ "○ A field of the provided bankslip was null or with invalid values"),
	RECORD_ALREADY_EXISTS("Record already exists"), INTERNAL_SERVER_ERROR("Internal server error"),
	NO_RECORD_FOUND("Record with provided id is not found"), AUTHENTICATION_FAILED("Authentication failed"),
	COULD_NOT_UPDATE_RECORD("Could not update record"), COULD_NOT_DELETE_RECORD("Could not delete record"),
	BANKSLIP_BAD_REQUEST("Bankslip not provided in the request body"),
	BANKSLIP_NOT_FOUND("Bankslip not found with the specified id.");

	private String errorMessage;

	ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
