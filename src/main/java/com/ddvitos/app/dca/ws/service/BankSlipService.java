package com.ddvitos.app.dca.ws.service;

import java.util.Date;
import java.util.List;

import com.ddvitos.app.dca.ws.shared.dto.BankSlipDTO;

public interface BankSlipService {

	BankSlipDTO createBankSlip(BankSlipDTO bankSlipDto);

	List<BankSlipDTO> getBankSlips(int page, int limit);

	BankSlipDTO getBankSlipByBankSlipId(String bankSlipId);

	void payment(String bankSlipId, Date paymentDate);

	void cancel(String bankSlipId);

}
