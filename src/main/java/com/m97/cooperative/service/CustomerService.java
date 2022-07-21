package com.m97.cooperative.service;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.m97.cooperative.model.CustomerAccountModel;
import com.m97.cooperative.model.CustomerModel;
import com.m97.cooperative.model.GenericModel;
import com.m97.cooperative.repository.CustomerAccountRepository;
import com.m97.cooperative.repository.CustomerRepository;
import com.m97.cooperative.util.CommonUtil;
import com.m97.cooperative.util.ResponseUtil;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	private static final Logger LOGGER = LogManager.getLogger();

	public ResponseEntity<Object> getAllData() {
		GenericModel genericModel = new GenericModel();

		try {
			List<CustomerModel> customerModels = customerRepository.getAllData();
			if (customerModels.isEmpty())
				genericModel.setCode("S002");
			else
				genericModel.setCode("S001");
			genericModel.setData(customerModels);
		} catch (DataAccessException e) {
			LOGGER.error("REPOSITORY", e);
			genericModel.setCode("E002");
		}

		return ResponseUtil.setResponse(genericModel);
	}

	@Transactional
	public ResponseEntity<Object> entryData(CustomerModel customerModel, String auth) {
		GenericModel genericModel = new GenericModel();

		LOGGER.info(customerModel);

		try {
			customerModel.setCustUuid(UUID.randomUUID().toString());
			customerModel.setCreatedBy(CommonUtil.getUsernameByAuth(auth));

			int rowsAffected = customerRepository.entryData(customerModel);
			if (rowsAffected > 0) {
				genericModel.setCode("S003");
				genericModel.setData(customerModel.getCustUuid());
			} else {
				genericModel.setCode("E002");
			}
		} catch (DataAccessException e) {
			LOGGER.error("REPOSITORY", e);
			genericModel.setCode("E002");
		} catch (Exception e) {
			LOGGER.error("SERVICE", e);
			genericModel.setCode("E001");
		}

		if ("S003".equals(genericModel.getCode())) {
			CustomerAccountModel customerAccountModel = new CustomerAccountModel();
			customerAccountModel.setCustUuid(customerModel.getCustUuid());
			customerAccountModel.setCreatedBy(customerModel.getCreatedBy());

			customerAccountModel.setAcctName("SAVINGS");
			customerAccountRepository.entryData(customerAccountModel);

			customerAccountModel.setAcctName("LOAN");
			customerAccountRepository.entryData(customerAccountModel);
		}

		return ResponseUtil.setResponse(genericModel);
	}

}
