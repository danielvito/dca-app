package com.ddvitos.app.dca.ws.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ddvitos.app.dca.ws.exceptions.InvalidBankSlipProvidedException;
import com.ddvitos.app.dca.ws.service.BankSlipService;
import com.ddvitos.app.dca.ws.shared.dto.BankSlipDTO;
import com.ddvitos.app.dca.ws.ui.model.request.BankSlipCreateRequestModel;
import com.ddvitos.app.dca.ws.ui.model.request.BankSlipPaymentRequestModel;
import com.ddvitos.app.dca.ws.ui.model.response.BankSlipDetailRest;
import com.ddvitos.app.dca.ws.ui.model.response.BankSlipPreviewRest;
import com.ddvitos.app.dca.ws.ui.model.response.ErrorMessages;

@RestController
@RequestMapping("/bankslips")
public class BankSlipsController {

	@Autowired
	BankSlipService bankSlipService;

	// criar boleto
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public BankSlipPreviewRest createBankSlip(@RequestBody BankSlipCreateRequestModel bankSlip) {
		if (bankSlip.getDueDate() == null)
			throw new InvalidBankSlipProvidedException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		if (bankSlip.getTotalInCents() == null)
			throw new InvalidBankSlipProvidedException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		if (bankSlip.getCustomer().isEmpty())
			throw new InvalidBankSlipProvidedException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		ModelMapper modelMapper = new ModelMapper();
		BankSlipDTO bankSlipDto = modelMapper.map(bankSlip, BankSlipDTO.class);

		BankSlipDTO createdBankSlip = bankSlipService.createBankSlip(bankSlipDto);
		return modelMapper.map(createdBankSlip, BankSlipPreviewRest.class);
	}

	// lista de boletos
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<BankSlipPreviewRest> listBankSlip(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<BankSlipPreviewRest> returnValue = new ArrayList<>();

		List<BankSlipDTO> bankSlips = bankSlipService.getBankSlips(page, limit);

		Type listType = new TypeToken<List<BankSlipPreviewRest>>() {
		}.getType();
		returnValue = new ModelMapper().map(bankSlips, listType);

		return returnValue;
	}

	// detalhe de um boleto
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public BankSlipDetailRest getBankSlip(@PathVariable String id) {
		BankSlipDetailRest returnValue = new BankSlipDetailRest();
		BankSlipDTO bankSlipDto = bankSlipService.getBankSlipByBankSlipId(id);
		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(bankSlipDto, BankSlipDetailRest.class);
		return returnValue;
	}

	// pagar boleto
	@PostMapping(path = "/{id}/payments", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void payBankSlip(@PathVariable String id, @RequestBody BankSlipPaymentRequestModel bankSlip) {
		if (bankSlip.getPaymentDate() == null)
			throw new InvalidBankSlipProvidedException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		bankSlipService.payment(id, bankSlip.getPaymentDate());
	}

	// cancelar boleto
	@DeleteMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelBankSlip(@PathVariable String id) {
		bankSlipService.cancel(id);
	}

}
