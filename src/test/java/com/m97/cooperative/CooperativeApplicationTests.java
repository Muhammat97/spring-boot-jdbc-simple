package com.m97.cooperative;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m97.cooperative.model.CustomerModel;
import com.m97.cooperative.model.TransactionModel;
import com.m97.cooperative.service.CustomerService;
import com.m97.cooperative.util.CommonUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CooperativeApplicationTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	CustomerService customerService;

	SimpleDateFormat sdf = new SimpleDateFormat(CommonUtil.PATTERN_DATE);

	ObjectMapper om = new ObjectMapper();

	static String basicAuth;

	static final String pathCode = "$.code";
	static final String pathOutput = "$.output";

	@BeforeAll
	void init() {
		basicAuth = "Basic ".concat(Base64.getEncoder().encodeToString("m97:P@ssw0rd".getBytes()));
		given(customerService.entryData(Mockito.any(CustomerModel.class), Mockito.anyString()))
				.willReturn(new ResponseEntity<Object>(HttpStatus.OK));
	}

	@Test
	void nEntryCustomerNameNull() throws Exception {
		CustomerModel model = new CustomerModel();
		model.setBirthDate(sdf.parse("1997-03-21"));
		model.setAddress("Kp. Cikedokan");
		model.setNeighbourhoodNum("002");
		model.setHamletNum("010");

		mvc.perform(post("/").header(HttpHeaders.AUTHORIZATION, basicAuth).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(model))).andDo(log()).andExpect(status().isBadRequest())
				.andExpect(jsonPath(pathCode).value("E006"));
	}

	@Test
	void nEntryCustomerNameMin3() throws Exception {
		CustomerModel model = new CustomerModel();
		model.setFullName("Mu");
		model.setBirthDate(sdf.parse("1997-03-21"));
		model.setAddress("Kp. Cikedokan");
		model.setNeighbourhoodNum("002");
		model.setHamletNum("010");

		mvc.perform(post("/").header(HttpHeaders.AUTHORIZATION, basicAuth).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(model))).andDo(log()).andExpect(status().isBadRequest())
				.andExpect(jsonPath(pathCode).value("E007"));
	}

	@Test
	void nEntryCustomerNameMax60() throws Exception {
		CustomerModel model = new CustomerModel();
		model.setFullName(
				"Muhammat Amir Munajad muhammat amir munajaad muhammat amir omunajad muhammat amir munajad muhammat amir munajad");
		model.setBirthDate(sdf.parse("1997-03-21"));
		model.setAddress("Kp. Cikedokan");
		model.setNeighbourhoodNum("002");
		model.setHamletNum("010");

		mvc.perform(post("/").header(HttpHeaders.AUTHORIZATION, basicAuth).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(model))).andDo(log()).andExpect(status().isBadRequest())
				.andExpect(jsonPath(pathCode).value("E008"));
	}

	@Test
	void nEntryCustomerBirthDateFuture() throws Exception {
		CustomerModel model = new CustomerModel();
		model.setFullName("Muhammat Amir Munajad");
		model.setBirthDate(sdf.parse("2097-03-21"));
		model.setAddress("Kp. Cikedokan");
		model.setNeighbourhoodNum("002");
		model.setHamletNum("010");

		mvc.perform(post("/").header(HttpHeaders.AUTHORIZATION, basicAuth).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(model))).andDo(log()).andExpect(status().isBadRequest())
				.andExpect(jsonPath(pathCode).value("E014"));
	}

	@Test
	void nEntryCustomerHamletNotNum() throws Exception {
		CustomerModel model = new CustomerModel();
		model.setFullName("Muhammat Amir Munajad");
		model.setBirthDate(sdf.parse("1997-03-21"));
		model.setAddress("Kp. Cikedokan");
		model.setNeighbourhoodNum("002");
		model.setHamletNum("ABC");

		mvc.perform(post("/").header(HttpHeaders.AUTHORIZATION, basicAuth).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(model))).andDo(log()).andExpect(status().isBadRequest())
				.andExpect(jsonPath(pathCode).value("E010"));
	}

	@Test
	void nGetAllTransactionStartDateGreater() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("startDate", "2022-07-07");
		requestParams.add("endDate", "2022-01-01");
		mvc.perform(get("/transaction").queryParams(requestParams).header(HttpHeaders.AUTHORIZATION, basicAuth))
				.andDo(log()).andExpect(status().isBadRequest()).andExpect(jsonPath(pathCode).value("E014"));
	}

	@Test
	void nTransactionNegativeAmount() throws Exception {
		TransactionModel model = new TransactionModel();
		model.setTranAmount(new BigDecimal(-1000));
		model.setTranType("LOAN");

		mvc.perform(post("/uuid/transaction").header(HttpHeaders.AUTHORIZATION, basicAuth)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(model))).andDo(log())
				.andExpect(status().isBadRequest()).andExpect(jsonPath(pathCode).value("E013"));
	}

	@Test
	void nTransactionWrongType() throws Exception {
		TransactionModel model = new TransactionModel();
		model.setTranAmount(new BigDecimal(1000000));
		model.setTranType("PINJAM");

		mvc.perform(post("/uuid/transaction").header(HttpHeaders.AUTHORIZATION, basicAuth)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(model))).andDo(log())
				.andExpect(status().isBadRequest()).andExpect(jsonPath(pathCode).value("E011"));
	}

}
