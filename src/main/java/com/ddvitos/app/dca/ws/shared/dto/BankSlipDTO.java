package com.ddvitos.app.dca.ws.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankSlipDTO implements Serializable {

	private static final long serialVersionUID = 6340711635005394303L;

	private long id;
	private String bankslipId;
	private Date dueDate;
	private BigDecimal totalInCents;
	private String customer;
	private String status;
	private Date paymentDate;
	private BigDecimal fine;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBankslipId() {
		return bankslipId;
	}

	public void setBankslipId(String bankslipId) {
		this.bankslipId = bankslipId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

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
