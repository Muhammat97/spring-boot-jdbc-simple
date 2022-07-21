package com.m97.cooperative.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m97.cooperative.model.CustomerModel;
import com.m97.cooperative.service.CustomerService;

@RestController
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/")
	public ResponseEntity<Object> getAllData() {
		return customerService.getAllData();
	}

	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> entryData(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth,
			@RequestBody @Valid CustomerModel customerModel) {
		return customerService.entryData(customerModel, auth);
	}

}
