package com.ddvitos.app.dca.ws.ui.model.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankSlipPaymentRequestModel {

	@JsonProperty("payment_date")
	private Date paymentDate;

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
