package com.m97.cooperative.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping(value = "/{custUuid}")
	public ResponseEntity<Object> getDataById(@PathVariable String custUuid) {
		return customerService.getDataById(custUuid);
	}

	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> entryData(Authentication authentication,
			@RequestBody @Valid CustomerModel customerModel) {
		return customerService.entryData(customerModel, authentication.getName());
	}

	@PutMapping(value = "/{custUuid}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> entryData(Authentication authentication, @PathVariable String custUuid,
			@RequestBody CustomerModel customerModel) {
		return customerService.updateDataById(custUuid, customerModel, authentication.getName());
	}

}
