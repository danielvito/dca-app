package com.ddvitos.app.dca.ws.ui.model.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankSlipPreviewRest {

	private String bankslipId;
	private Date dueDate;
	private BigDecimal totalInCents;
	private String customer;
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

}
