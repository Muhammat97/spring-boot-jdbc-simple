package com.m97.cooperative.repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.m97.cooperative.model.TransactionModel;

@Repository
public class TransactionRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = LogManager.getLogger();

	public List<TransactionModel> getAllData(Date startDate, Date endDate) {
		String queryWhere = "";
		Object[] values = new Object[] {};
		if (startDate != null && endDate != null) {
			queryWhere = queryWhere.concat(" AND tran_date >= ? AND tran_date < ?");
			values = new Object[] { startDate, endDate };
		}

		String query = String.format("SELECT tran_uuid, tran_type, tran_amount, tran_date, created_by, "
				+ "(SELECT full_name FROM test.cust WHERE cust_id = ("
				+ "SELECT cust_id FROM test.cust_acct WHERE cust_acct_id = tran.cust_acct_id)) AS full_name "
				+ "FROM test.tran WHERE 1=1%s ORDER BY tran_date", queryWhere);
		LOGGER.debug(query);

		LOGGER.debug(Arrays.toString(values));

		return jdbcTemplate.query(query, DataClassRowMapper.newInstance(TransactionModel.class), values);
	}

	public List<TransactionModel> getDataByCustAcctIds(List<Integer> custAcctIds) {
		String inSql = String.join(", ", Collections.nCopies(custAcctIds.size(), "?"));
		String query = String.format("SELECT tran_uuid, cust_acct_id, tran_type, tran_amount, tran_date, created_by "
				+ "FROM test.tran WHERE cust_acct_id IN (%s) ORDER BY tran_date", inSql);
		LOGGER.debug(query);

		LOGGER.debug(custAcctIds);

		return jdbcTemplate.query(query, DataClassRowMapper.newInstance(TransactionModel.class), custAcctIds.toArray());
	}

	public int entryData(TransactionModel model) {
		String query = "INSERT INTO test.tran(tran_uuid, cust_acct_id, tran_type, tran_amount, created_by) VALUES("
				+ String.join(", ", Collections.nCopies(5, "?")) + ")";
		LOGGER.debug(query);

		Object[] values = new Object[] { model.getTranUuid(), model.getCustAcctId(), model.getTranType(),
				model.getTranAmount(), model.getCreatedBy() };
		LOGGER.debug(Arrays.toString(values));

		return jdbcTemplate.update(query, values);
	}

}
