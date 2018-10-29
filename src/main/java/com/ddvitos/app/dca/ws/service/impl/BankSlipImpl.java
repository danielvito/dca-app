package com.ddvitos.app.dca.ws.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ddvitos.app.dca.ws.exceptions.BankSlipNotFoundException;
import com.ddvitos.app.dca.ws.io.entity.BankSlipEntity;
import com.ddvitos.app.dca.ws.io.entity.BankSlipStatus;
import com.ddvitos.app.dca.ws.io.repository.BankSlipRepository;
import com.ddvitos.app.dca.ws.service.BankSlipService;
import com.ddvitos.app.dca.ws.shared.Utils;
import com.ddvitos.app.dca.ws.shared.dto.BankSlipDTO;
import com.ddvitos.app.dca.ws.ui.model.response.ErrorMessages;

@Service
public class BankSlipImpl implements BankSlipService {

	@Autowired
	BankSlipRepository bankSlipRepository;

	@Autowired
	Utils utils;

	@Override
	public BankSlipDTO createBankSlip(BankSlipDTO bankSlipDto) {
		ModelMapper modelMapper = new ModelMapper();
		BankSlipEntity bankSlipEntity = modelMapper.map(bankSlipDto, BankSlipEntity.class);

		String bankSlipId = utils.generateId(30);
		bankSlipEntity.setBankslipId(bankSlipId);
		bankSlipEntity.setStatus(BankSlipStatus.PENDING);
		bankSlipEntity.setFine(BigDecimal.valueOf(0));

		BankSlipEntity storedbankSlipDetails = bankSlipRepository.save(bankSlipEntity);

		return modelMapper.map(storedbankSlipDetails, BankSlipDTO.class);
	}

	@Override
	public List<BankSlipDTO> getBankSlips(int page, int limit) {
		if (page > 0)
			page--;

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<BankSlipEntity> bankSlipsPage = bankSlipRepository.findAll(pageableRequest);
		List<BankSlipEntity> bankSlips = bankSlipsPage.getContent();

		ModelMapper modelMapper = new ModelMapper();

		List<BankSlipDTO> returnValue = new ArrayList<>();
		for (BankSlipEntity bankSlipEntity : bankSlips) {
			returnValue.add(modelMapper.map(bankSlipEntity, BankSlipDTO.class));
		}

		return returnValue;
	}

	@Override
	public BankSlipDTO getBankSlipByBankSlipId(String bankSlipId) {
		BankSlipDTO returnValue = new BankSlipDTO();
		BankSlipEntity bankSlipEntity = bankSlipRepository.findBybankslipId(bankSlipId);
		if (bankSlipEntity == null)
			throw new BankSlipNotFoundException(ErrorMessages.BANKSLIP_NOT_FOUND.getErrorMessage());

		// check fine value
		if (bankSlipEntity.getStatus().equals(BankSlipStatus.PENDING)) {
			BigDecimal fine = calculateFine(new Date(), bankSlipEntity.getDueDate(), bankSlipEntity.getTotalInCents());
			// fine.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			bankSlipEntity.setFine(fine);
		}

		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(bankSlipEntity, BankSlipDTO.class);

		return returnValue;
	}

	private static BigDecimal calculateFine(Date paymentDate, Date dueDate, BigDecimal totalInCents) {
		long diff = paymentDate.getTime() - dueDate.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		if (days > 0 && days <= 10)
			return totalInCents.multiply(new BigDecimal(0.005));
		if (days > 10)
			return totalInCents.multiply(new BigDecimal(0.01));
		return BigDecimal.valueOf(0);
	}

	@Override
	public void payment(String bankSlipId, Date paymentDate) {
		BankSlipEntity bankSlipEntity = bankSlipRepository.findBybankslipId(bankSlipId);
		if (bankSlipEntity == null)
			throw new BankSlipNotFoundException(ErrorMessages.BANKSLIP_NOT_FOUND.getErrorMessage());

		if (bankSlipEntity.getStatus().equals(BankSlipStatus.PENDING)) {
			bankSlipEntity
					.setFine(calculateFine(paymentDate, bankSlipEntity.getDueDate(), bankSlipEntity.getTotalInCents()));
			bankSlipEntity.setPaymentDate(paymentDate);
			bankSlipEntity.setStatus(BankSlipStatus.PAID);

			bankSlipRepository.save(bankSlipEntity);
		}
	}

	@Override
	public void cancel(String bankSlipId) {
		BankSlipEntity bankSlipEntity = bankSlipRepository.findBybankslipId(bankSlipId);
		if (bankSlipEntity == null)
			throw new BankSlipNotFoundException(ErrorMessages.BANKSLIP_NOT_FOUND.getErrorMessage());

		if (bankSlipEntity.getStatus().equals(BankSlipStatus.PENDING)) {
			bankSlipEntity.setStatus(BankSlipStatus.CANCELLED);
			bankSlipRepository.save(bankSlipEntity);
		}
	}

}
