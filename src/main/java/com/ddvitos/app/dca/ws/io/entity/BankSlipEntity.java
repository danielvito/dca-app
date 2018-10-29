package com.ddvitos.app.dca.ws.io.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "bankslips")
public class BankSlipEntity implements Serializable {

	private static final long serialVersionUID = 4575728921030572550L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String bankslipId;

	@Column(nullable = false)
	private Date dueDate;

	@Column(nullable = false)
	private BigDecimal totalInCents;

	@Column(nullable = false, length = 250)
	private String customer;

	@Column(nullable = true)
	private Date paymentDate;

	@Column(nullable = false)
	private BigDecimal fine;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private BankSlipStatus status;

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

	public BankSlipStatus getStatus() {
		return status;
	}

	public void setStatus(BankSlipStatus status) {
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
