package com.ddvitos.app.dca.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ddvitos.app.dca.ws.ui.model.response.ErrorMessage;
import com.ddvitos.app.dca.ws.ui.model.response.ErrorMessages;

@ControllerAdvice
public class AppExceptionsHandler {

	@ExceptionHandler(value = { BankSlipServiceException.class })
	public ResponseEntity<Object> handleUserServiceException(BankSlipServiceException ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { BankSlipNotFoundException.class })
	public ResponseEntity<Object> handleInvalidBankSlipNotFoundException(BankSlipNotFoundException ex,
			WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { InvalidBankSlipProvidedException.class })
	public ResponseEntity<Object> handleInvalidBankSlipProvidedException(InvalidBankSlipProvidedException ex,
			WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(),
				ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public ResponseEntity<Object> handleBankSlipBodyException(HttpMessageNotReadableException ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ErrorMessages.BANKSLIP_BAD_REQUEST.getErrorMessage());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
