package com.m97.cooperative.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Base64;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.m97.cooperative.model.CustomerModel;
import com.m97.cooperative.model.TransactionModel;
import com.m97.cooperative.util.CommonUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class A1Tests {

	@Autowired
	MockMvc mvc;

	SimpleDateFormat sdf = new SimpleDateFormat(CommonUtil.PATTERN_DATE);

	ObjectMapper om = new ObjectMapper();

	static String basicAuth;
	static String custUuid;
	static String tranUuid;

	static final String PATH_CODE = "$.code";
	static final String PATH_OUTPUT = "$.output";

	@BeforeAll
	static void init() {
		basicAuth = "Basic ".concat(Base64.getEncoder().encodeToString("m97:P@ssw0rd".getBytes()));
	}

	@Test
	@Order(1)
	void entryCustomer() throws Exception {
		CustomerModel model = new CustomerModel();
		model.setFullName("Muhammat Amir Munajad");
		model.setBirthDate(sdf.parse("1997-03-21"));
		model.setAddress("Kp. Cikedokan");
		model.setNeighbourhoodNum("002");
		model.setHamletNum("010");

		MvcResult result = mvc
				.perform(post("/").header(HttpHeaders.AUTHORIZATION, basicAuth)
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(model)))
				.andDo(log()).andExpect(status().isOk()).andExpect(jsonPath(PATH_CODE).value("S003")).andReturn();
		custUuid = JsonPath.read(result.getResponse().getContentAsString(), PATH_OUTPUT);
		assertThat(custUuid).hasSize(36);
	}

	@Test
	@Order(2)
	void getAllCustomer() throws Exception {
		mvc.perform(get("/").header(HttpHeaders.AUTHORIZATION, basicAuth)).andDo(log()).andExpect(status().isOk())
				.andExpect(jsonPath(PATH_CODE).value("S001"))
				.andExpect(jsonPath(PATH_OUTPUT.concat("[-1:].fullName")).value("Muhammat Amir Munajad"))
				.andExpect(jsonPath(PATH_OUTPUT.concat("[-1:].custUuid")).value(custUuid));
	}

	@Test
	@Order(3)
	void createTransaction() throws Exception {
		TransactionModel model = new TransactionModel();
		model.setTranAmount(new BigDecimal(18000000));
		model.setTranType("DEPOSIT");

		MvcResult result = mvc
				.perform(post("/".concat(custUuid).concat("/transaction")).header(HttpHeaders.AUTHORIZATION, basicAuth)
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(model)))
				.andDo(log()).andExpect(status().isOk()).andExpect(jsonPath(PATH_CODE).value("S003")).andReturn();
		tranUuid = JsonPath.read(result.getResponse().getContentAsString(), PATH_OUTPUT);
		assertThat(tranUuid).hasSize(36);
	}

	@Test
	@Order(4)
	void getAllTransaction() throws Exception {
		mvc.perform(get("/transaction").header(HttpHeaders.AUTHORIZATION, basicAuth)).andDo(log())
				.andExpect(status().isOk()).andExpect(jsonPath(PATH_CODE).value("S001"))
				.andExpect(jsonPath(PATH_OUTPUT.concat("[-1:].tranType")).value("DEPOSIT"))
				.andExpect(jsonPath(PATH_OUTPUT.concat("[-1:].tranUuid")).value(tranUuid));
	}

	@Test
	@Order(5)
	void getCustomerTransaction() throws Exception {
		mvc.perform(get("/".concat(custUuid).concat("/transaction")).header(HttpHeaders.AUTHORIZATION, basicAuth))
				.andDo(log()).andExpect(status().isOk()).andExpect(jsonPath(PATH_CODE).value("S001"))
				.andExpect(jsonPath(PATH_OUTPUT.concat("[-1:].tranType")).value("DEPOSIT"))
				.andExpect(jsonPath(PATH_OUTPUT.concat("[-1:].tranUuid")).value(tranUuid));
	}

	@Test
	void nGetCustomerTransactionNotFound() throws Exception {
		mvc.perform(get("/uuid-not-found/transaction").header(HttpHeaders.AUTHORIZATION, basicAuth)).andDo(log())
				.andExpect(status().isNotFound()).andExpect(jsonPath(PATH_CODE).value("E003"));
	}

}