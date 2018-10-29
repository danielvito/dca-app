package com.ddvitos.app.dca.ws.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ddvitos.app.dca.ws.io.entity.BankSlipEntity;

@Repository
public interface BankSlipRepository extends PagingAndSortingRepository<BankSlipEntity, Long> {

	BankSlipEntity findBybankslipId(String bankSlipId);

}
