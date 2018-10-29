package com.ddvitos.app.dca.ws.ui.model.response;

import java.math.BigDecimal;
import java.util.Date;

import com.ddvitos.app.dca.ws.shared.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class BankSlipDetailRest {

	private String bankslipId;
	private Date dueDate;
	private Date paymentDate;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal totalInCents;
	private String customer;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal fine;
	private String status;

	@JsonProperty("id")
	public String getBankslipId() {
		return bankslipId;
	}

	public void setBankslipId(String bankslipId) {
		this.bankslipId = bankslipId;
	}

	@JsonProperty("duo_date")
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@JsonProperty("total_in_cents")
	public BigDecimal getTotalInCents() {
		return totalInCents;
	}

	public void setTotalInCents(BigDecimal totalInCents) {
		this.totalInCents = totalInCents;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("payment_date")
	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getFine() {
		return fine;
	}

	public void setFine(BigDecimal fine) {
		this.fine = fine;
	}

}
