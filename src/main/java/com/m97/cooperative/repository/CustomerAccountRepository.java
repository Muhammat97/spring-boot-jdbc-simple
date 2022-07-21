package com.m97.cooperative.repository;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.m97.cooperative.model.CustomerAccountModel;

@Repository
public class CustomerAccountRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = LogManager.getLogger();

	public int entryData(CustomerAccountModel model) {
		String query = "INSERT INTO test.cust_acct (cust_id, acct_id, balance, created_by) VALUES ("
				+ "(SELECT cust_id FROM test.cust WHERE cust_uuid = ?), "
				+ "(SELECT acct_id FROM test.acct WHERE acct_name = ?), ?, ?)";
		LOGGER.debug(query);

		Object[] values = new Object[] { model.getCustUuid(), model.getAcctName(), model.getBalance(),
				model.getCreatedBy() };
		LOGGER.debug(Arrays.toString(values));

		return jdbcTemplate.update(query, values);
	}

	public int updateBalanceById(Integer id, CustomerAccountModel model) {
		String query = "UPDATE test.cust_acct SET balance = ?, updated_at = NOW(), updated_by = ? "
				+ "WHERE cust_acct_id = ?";
		LOGGER.debug(query);

		Object[] values = new Object[] { model.getBalance(), model.getCreatedBy(), id };
		LOGGER.debug(Arrays.toString(values));

		return jdbcTemplate.update(query, values);
	}

	public List<Integer> getCustAcctIdsByCustUuid(String custUuid) {
		String query = "SELECT cust_acct_id FROM test.cust_acct "
				+ "WHERE cust_id = (SELECT cust_id FROM test.cust WHERE cust_uuid = ?)";
		LOGGER.debug(query);

		Object[] values = new Object[] { custUuid };
		LOGGER.debug(Arrays.toString(values));

		return jdbcTemplate.queryForList(query, Integer.class, values);
	}

	public CustomerAccountModel getDataByCustUuidAndAcctName(String custUuid, String acctName) {
		String query = "SELECT cust_acct_id, balance FROM test.cust_acct "
				+ "WHERE cust_id = (SELECT cust_id FROM test.cust WHERE cust_uuid = ?) "
				+ "AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = ?)";
		LOGGER.debug(query);

		Object[] values = new Object[] { custUuid, acctName };
		LOGGER.debug(Arrays.toString(values));

		return jdbcTemplate.queryForObject(query, DataClassRowMapper.newInstance(CustomerAccountModel.class), values);
	}

}
