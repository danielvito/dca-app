package com.ddvitos.app.dca.ws.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ddvitos.app.dca.ws.exceptions.BankSlipNotFoundException;
import com.ddvitos.app.dca.ws.io.entity.BankSlipEntity;
import com.ddvitos.app.dca.ws.io.entity.BankSlipStatus;
import com.ddvitos.app.dca.ws.io.repository.BankSlipRepository;
import com.ddvitos.app.dca.ws.shared.dto.BankSlipDTO;

class BankSlipServiceImpTest {

	@InjectMocks
	BankSlipServiceImpl bankSlipService;

	@Mock
	BankSlipRepository bankSlipRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Service Get BankSlip Unit Test")
	void testGetBankSlip() {
		BankSlipEntity bankSlipEntity = new BankSlipEntity();
		bankSlipEntity.setBankslipId("1234");
		bankSlipEntity.setCustomer("Customer");
		bankSlipEntity.setStatus(BankSlipStatus.PENDING);
		bankSlipEntity.setTotalInCents(BigDecimal.valueOf(1000000));
		bankSlipEntity.setDueDate(new Date());

		when(bankSlipRepository.findBybankslipId(anyString())).thenReturn(bankSlipEntity);

		BankSlipDTO bankSlipDTO = bankSlipService.getBankSlipByBankSlipId("1234");

		assertNotNull(bankSlipDTO);
		assertEquals("Customer", bankSlipDTO.getCustomer());
	}

	@Test
	@DisplayName("Service Get BankSlip BankSlipNotFoundException Unit Test")
	void testGetBankSlip_BankSlipNotFoundException() {
		when(bankSlipRepository.findBybankslipId(anyString())).thenReturn(null);
		assertThrows(BankSlipNotFoundException.class, () -> {
			bankSlipService.getBankSlipByBankSlipId("1234");
		});
	}

}
