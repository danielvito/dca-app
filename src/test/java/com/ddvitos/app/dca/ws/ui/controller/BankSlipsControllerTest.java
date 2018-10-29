package com.ddvitos.app.dca.ws.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ddvitos.app.dca.ws.io.entity.BankSlipStatus;
import com.ddvitos.app.dca.ws.service.impl.BankSlipServiceImpl;
import com.ddvitos.app.dca.ws.shared.dto.BankSlipDTO;
import com.ddvitos.app.dca.ws.ui.model.response.BankSlipDetailRest;

class BankSlipsControllerTest {

	@InjectMocks
	BankSlipsController bankSlipsController;

	@Mock
	BankSlipServiceImpl bankSlipService;

	BankSlipDTO bankSlipDTO;

	final String BANKSLIP_ID = "1234";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		bankSlipDTO = new BankSlipDTO();
		bankSlipDTO.setBankslipId(BANKSLIP_ID);
		bankSlipDTO.setCustomer("Customer");
		bankSlipDTO.setStatus(BankSlipStatus.PENDING.toString());
		bankSlipDTO.setTotalInCents(BigDecimal.valueOf(1000000));
		bankSlipDTO.setDueDate(new Date());
	}

	@Test
	void testGetBankSlip() {
		when(bankSlipService.getBankSlipByBankSlipId(anyString())).thenReturn(bankSlipDTO);

		BankSlipDetailRest bankSlipRest = bankSlipsController.getBankSlip(BANKSLIP_ID);

		assertNotNull(bankSlipRest);
		assertEquals(BANKSLIP_ID, bankSlipRest.getBankslipId());
		assertEquals(bankSlipDTO.getCustomer(), bankSlipRest.getCustomer());
		assertEquals(bankSlipDTO.getDueDate(), bankSlipRest.getDueDate());
	}

}
