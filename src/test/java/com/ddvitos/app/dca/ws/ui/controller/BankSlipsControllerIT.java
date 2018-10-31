package com.ddvitos.app.dca.ws.ui.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.ddvitos.app.dca.ws.io.entity.BankSlipStatus;
import com.ddvitos.app.dca.ws.ui.model.response.BankSlipDetailRest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BankSlipsControllerIT {

	public BankSlipsControllerIT() {
	}

	private final String CONTEXT_PATH = "/rest";

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}

	private Response insertBankSlip() {
		Map<String, Object> bankSlip = new HashMap<String, Object>();
		bankSlip.put("due_date", "2018-10-01");
		bankSlip.put("total_in_cents", "100000");
		bankSlip.put("customer", "Trillian Company");
		Response response = given().contentType("application/json").accept("application/json").body(bankSlip).when()
				.post(CONTEXT_PATH + "/bankslips").then().statusCode(201).contentType("application/json").extract()
				.response();
		return response;
	}

	@Test
	@DisplayName("Create BankSlip")
	public void testCreateBankSlip() {
		Map<String, Object> bankSlip = new HashMap<String, Object>();
		bankSlip.put("due_date", "2018-10-01");
		bankSlip.put("total_in_cents", "100000");
		bankSlip.put("customer", "Trillian Company");
		Response response = given().contentType("application/json").accept("application/json").body(bankSlip).when()
				.post(CONTEXT_PATH + "/bankslips").then().statusCode(201).contentType("application/json").extract()
				.response();
		String bankSlipId = response.jsonPath().getString("id");
		assertNotNull(bankSlipId);
	}

	@Test
	@DisplayName("Get BankSlip Unprocessable Bad Request")
	public void testCreateBankSlip_BadRequest() {
		given().contentType("application/json").accept("application/json").when().post(CONTEXT_PATH + "/bankslips")
				.then().statusCode(400).contentType("application/json").extract().response();
	}

	@Test
	@DisplayName("Get BankSlip Unprocessable Entity")
	public void testCreateBankSlip_UnprocessableEntity() {
		Map<String, Object> bankSlip = new HashMap<String, Object>();
		bankSlip.put("due_date", "2018-10-01");
		given().contentType("application/json").accept("application/json").body(bankSlip).when()
				.post(CONTEXT_PATH + "/bankslips").then().statusCode(422).contentType("application/json").extract()
				.response();
	}

	@Test
	@DisplayName("Get BankSlip List")
	public void testListBankSlip() {
		// insert
		insertBankSlip();

		// list
		Response response = given().contentType("application/json").accept("application/json").when()
				.get(CONTEXT_PATH + "/bankslips").then().statusCode(200).contentType("application/json").extract()
				.response();

		List<BankSlipDetailRest> bankSlipList = response.jsonPath().getList("", BankSlipDetailRest.class);
		assertTrue(bankSlipList.size() >= 1);
	}

	@Test
	@DisplayName("Get BankSlip Details")
	public void testGetBankSlip() {
		// insert
		Response responseInsert = insertBankSlip();
		BankSlipDetailRest bankSlipInsert = responseInsert.jsonPath().getObject("", BankSlipDetailRest.class);
		String bankSlipId = bankSlipInsert.getBankslipId();

		// detail
		Response responseDetail = given().contentType("application/json").accept("application/json").when()
				.get(CONTEXT_PATH + "/bankslips/" + bankSlipId).then().statusCode(200).contentType("application/json")
				.extract().response();
		BankSlipDetailRest bankSlipDetail = responseDetail.jsonPath().getObject("", BankSlipDetailRest.class);

		assertEquals(bankSlipInsert.getBankslipId(), bankSlipDetail.getBankslipId());
	}

	@Test
	@DisplayName("Pay BankSlip")
	public void testPayBankSlip() {
		// insert
		Response responseInsert = insertBankSlip();
		BankSlipDetailRest bankSlipInsert = responseInsert.jsonPath().getObject("", BankSlipDetailRest.class);
		String bankSlipId = bankSlipInsert.getBankslipId();

		// payment
		Map<String, Object> bankSlip = new HashMap<String, Object>();
		bankSlip.put("payment_date", "2019-10-10");
		given().contentType("application/json").accept("application/json").body(bankSlip).when()
				.post(CONTEXT_PATH + "/bankslips/" + bankSlipId + "/payments").then().statusCode(204);

		// detail
		Response responseDetail = given().contentType("application/json").accept("application/json").when()
				.get(CONTEXT_PATH + "/bankslips/" + bankSlipId).then().statusCode(200).contentType("application/json")
				.extract().response();
		BankSlipDetailRest bankSlipDetail = responseDetail.jsonPath().getObject("", BankSlipDetailRest.class);

		assertEquals(bankSlipDetail.getStatus(), BankSlipStatus.PAID.toString());
	}

	@Test
	@DisplayName("Cancel BankSlip")
	public void testCancelBankSlip() {
		// insert
		Response responseInsert = insertBankSlip();
		BankSlipDetailRest bankSlipInsert = responseInsert.jsonPath().getObject("", BankSlipDetailRest.class);
		String bankSlipId = bankSlipInsert.getBankslipId();

		// cancel
		given().contentType("application/json").accept("application/json").when()
				.delete(CONTEXT_PATH + "/bankslips/" + bankSlipId).then().statusCode(204);

		// detail
		Response responseDetail = given().contentType("application/json").accept("application/json").when()
				.get(CONTEXT_PATH + "/bankslips/" + bankSlipId).then().statusCode(200).contentType("application/json")
				.extract().response();
		BankSlipDetailRest bankSlipDetail = responseDetail.jsonPath().getObject("", BankSlipDetailRest.class);

		assertEquals(bankSlipDetail.getStatus(), BankSlipStatus.CANCELLED.toString());
	}

}
