package com.m97.cooperative.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.m97.cooperative.model.TransactionModel;
import com.m97.cooperative.service.TransactionService;

@RestController
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping(value = "/transaction")
	public ResponseEntity<Object> getAllData(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate) {
		return transactionService.getAllData(startDate, endDate);
	}

	@GetMapping(value = "/{custUuid}/transaction")
	public ResponseEntity<Object> getAllData(@PathVariable String custUuid) {
		return transactionService.getDataByCustUuid(custUuid);
	}

	@PostMapping(value = "/{custUuid}/transaction", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> entryData(Authentication authentication, @PathVariable String custUuid,
			@RequestBody @Valid TransactionModel transactionModel) {
		return transactionService.entryData(custUuid, transactionModel, authentication.getName());
	}

}
