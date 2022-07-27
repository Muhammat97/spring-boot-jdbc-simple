package com.m97.cooperative.service;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.m97.cooperative.model.CustomerAccountModel;
import com.m97.cooperative.model.CustomerModel;
import com.m97.cooperative.model.GenericModel;
import com.m97.cooperative.model.TransactionModel;
import com.m97.cooperative.repository.CustomerAccountRepository;
import com.m97.cooperative.repository.CustomerRepository;
import com.m97.cooperative.repository.TransactionRepository;
import com.m97.cooperative.util.ResponseUtil;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

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

	public ResponseEntity<Object> getDataById(String custUuid) {
		GenericModel genericModel = new GenericModel();

		try {
			CustomerModel customerModel = customerRepository.getDataById(custUuid);

			if (customerModel != null) {
				List<CustomerAccountModel> customerAccountModels = customerAccountRepository
						.getDataByCustUuid(custUuid);
				customerModel.setCustomerAccount(customerAccountModels);

				List<Integer> listCustAcctId = customerAccountRepository.getCustAcctIdsByCustUuid(custUuid);
				List<TransactionModel> transactionModels = transactionRepository.getDataByCustAcctIds(listCustAcctId);
				customerModel.setTransaction(transactionModels);
			}

			genericModel.setCode("S001");
			genericModel.setData(customerModel);
		} catch (IncorrectResultSizeDataAccessException e) {
			genericModel.setCode("E003");
		} catch (DataAccessException e) {
			LOGGER.error("REPOSITORY", e);
			genericModel.setCode("E002");
		}

		return ResponseUtil.setResponse(genericModel);
	}

	@Transactional
	public ResponseEntity<Object> entryData(CustomerModel customerModel, String username) {
		GenericModel genericModel = new GenericModel();

		LOGGER.info(customerModel);

		try {
			customerModel.setCustUuid(UUID.randomUUID().toString());
			customerModel.setCreatedBy(username);

			int rowsAffected = customerRepository.entryData(customerModel);
			if (rowsAffected < 1) {
				genericModel.setCode("E002");
				return ResponseUtil.setResponse(genericModel);
			}
		} catch (DataAccessException e) {
			LOGGER.error("REPOSITORY", e);
			genericModel.setCode("E002");
			return ResponseUtil.setResponse(genericModel);
		}

		CustomerAccountModel customerAccountModel = new CustomerAccountModel();
		customerAccountModel.setCustUuid(customerModel.getCustUuid());
		customerAccountModel.setCreatedBy(customerModel.getCreatedBy());

		customerAccountModel.setAcctName("SAVINGS");
		customerAccountRepository.entryData(customerAccountModel);

		customerAccountModel.setAcctName("LOAN");
		customerAccountRepository.entryData(customerAccountModel);

		genericModel.setCode("S003");
		genericModel.setData(customerModel.getCustUuid());

		return ResponseUtil.setResponse(genericModel);
	}

	public ResponseEntity<Object> updateDataById(String custUuid, CustomerModel customerModel, String username) {
		GenericModel genericModel = new GenericModel();

		try {
			customerModel.setUpdatedBy(username);

			int rowsAffected = customerRepository.updateDataById(custUuid, customerModel);
			if (rowsAffected > 0) {
				genericModel.setCode("S004");
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

		return ResponseUtil.setResponse(genericModel);
	}

}
