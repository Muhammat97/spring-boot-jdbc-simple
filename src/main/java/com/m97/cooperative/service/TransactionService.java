package com.m97.cooperative.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.util.StringUtils;

import com.m97.cooperative.model.CustomerAccountModel;
import com.m97.cooperative.model.GenericModel;
import com.m97.cooperative.model.TransactionModel;
import com.m97.cooperative.model.exception.CustomRuntimeException;
import com.m97.cooperative.model.props.MapCollectionProps;
import com.m97.cooperative.repository.CustomerAccountRepository;
import com.m97.cooperative.repository.TransactionRepository;
import com.m97.cooperative.util.CommonUtil;
import com.m97.cooperative.util.ResponseUtil;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	@Autowired
	private MapCollectionProps mapCollectionProps;

	private static final Logger LOGGER = LogManager.getLogger();

	public ResponseEntity<Object> getAllData(String startDate, String endDate) {
		GenericModel genericModel = new GenericModel();
		Date sDate = null;
		Date eDate = null;

		if (StringUtils.hasLength(startDate) || StringUtils.hasLength(endDate)) {
			if (!StringUtils.hasLength(startDate)) {
				genericModel.setCode("E006");
				genericModel.setArgs1("startDate");
				return ResponseUtil.setResponse(genericModel);
			}
			if (!StringUtils.hasLength(endDate)) {
				genericModel.setCode("E006");
				genericModel.setArgs1("endDate");
				return ResponseUtil.setResponse(genericModel);
			}

			try {
				SimpleDateFormat sdf = new SimpleDateFormat(CommonUtil.PATTERN_DATE);
				sdf.setTimeZone(CommonUtil.TIME_ZONE);
				sDate = sdf.parse(startDate);
				eDate = sdf.parse(endDate);

				if (sDate.after(eDate)) {
					genericModel.setCode("E014");
					genericModel.setArgs1("startDate");
					genericModel.setArgs2("endDate");
					return ResponseUtil.setResponse(genericModel);
				}

				Calendar calEndDate = Calendar.getInstance();
				calEndDate.setTime(eDate);
				calEndDate.add(Calendar.DATE, 1);

				eDate = calEndDate.getTime();
			} catch (Exception e) {
				genericModel.setCode("E012");
				genericModel.setArgs1("startDate and endDate");
				genericModel.setArgs2(CommonUtil.PATTERN_DATE);
				return ResponseUtil.setResponse(genericModel);
			}
		}

		try {
			List<TransactionModel> transactionModels = transactionRepository.getAllData(sDate, eDate);
			if (transactionModels.isEmpty())
				genericModel.setCode("S002");
			else
				genericModel.setCode("S001");
			genericModel.setData(transactionModels);
		} catch (DataAccessException e) {
			LOGGER.error("REPOSITORY", e);
			genericModel.setCode("E002");
		}

		return ResponseUtil.setResponse(genericModel);
	}

	public ResponseEntity<Object> getDataByCustUuid(String custUuid) {
		GenericModel genericModel = new GenericModel();

		try {
			List<Integer> custAcctids = customerAccountRepository.getCustAcctIdsByCustUuid(custUuid);
			if (custAcctids.isEmpty()) {
				genericModel.setCode("E003");
			} else {
				List<TransactionModel> transactionModels = transactionRepository.getDataByCustAcctIds(custAcctids);
				if (transactionModels.isEmpty())
					genericModel.setCode("S002");
				else
					genericModel.setCode("S001");
				genericModel.setData(transactionModels);
			}
		} catch (DataAccessException e) {
			LOGGER.error("REPOSITORY", e);
			genericModel.setCode("E002");
		}

		return ResponseUtil.setResponse(genericModel);
	}

	@Transactional
	public ResponseEntity<Object> entryData(String custUuid, TransactionModel model, String auth) {
		GenericModel genericModel = new GenericModel();

		LOGGER.info(model);

		CustomerAccountModel customerAccountModel = null;
		try {
			customerAccountModel = customerAccountRepository.getDataByCustUuidAndAcctName(custUuid,
					mapCollectionProps.getTransactionType().get(model.getTranType()));
		} catch (IncorrectResultSizeDataAccessException e) {
			genericModel.setCode("E003");
		} catch (DataAccessException e) {
			LOGGER.error("REPOSITORY", e);
			genericModel.setCode("E002");
		}

		if (customerAccountModel == null) {
			genericModel.setCode("E003");
		} else {
			try {
				model.setTranUuid(UUID.randomUUID().toString());
				model.setCreatedBy(CommonUtil.getUsernameByAuth(auth));
				model.setCustAcctId(customerAccountModel.getCustAcctId());

				int rowsAffected = transactionRepository.entryData(model);
				if (rowsAffected > 0) {
					genericModel.setCode("S003");
					genericModel.setData(model.getTranUuid());
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
				BigDecimal amount = model.getTranAmount();
				if ("WITHDRAWAL".equals(model.getTranType()) || "REFUND".equals(model.getTranType()))
					amount.negate();

				customerAccountModel.setBalance(
						customerAccountModel.getBalance() != null ? customerAccountModel.getBalance().add(amount)
								: amount);
				customerAccountModel.setUpdatedBy(model.getCreatedBy());

				int rowsAffected = customerAccountRepository.updateBalanceById(100, customerAccountModel);
				if (rowsAffected < 1)
					throw new CustomRuntimeException("E002");
			}
		}

		return ResponseUtil.setResponse(genericModel);
	}

}
