package com.m97.cooperative.repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.m97.cooperative.model.CustomerModel;
import com.m97.cooperative.util.CommonUtil;

@Repository
public class CustomerRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = LogManager.getLogger();

	public List<CustomerModel> getAllData() {
		String query = "SELECT cust_uuid, full_name, identity_num, birth_date, birth_place, "
				+ "address, neighbourhood_num, hamlet_num, village, subdistrict, "
				+ "city, province, zip_code, created_at, created_by, updated_at, updated_by "
				+ "FROM test.cust ORDER BY cust_id";
		LOGGER.debug(query);

		return jdbcTemplate.query(query, DataClassRowMapper.newInstance(CustomerModel.class));
	}

	public CustomerModel getDataById(String custUuid) {
		String query = "SELECT cust_uuid, full_name, identity_num, birth_date, birth_place, "
				+ "address, neighbourhood_num, hamlet_num, village, subdistrict, "
				+ "city, province, zip_code, created_at, created_by, updated_at, updated_by "
				+ "FROM test.cust WHERE cust_uuid = ?";
		LOGGER.debug(query);

		return jdbcTemplate.queryForObject(query, DataClassRowMapper.newInstance(CustomerModel.class), custUuid);
	}

	public int entryData(CustomerModel customerModel) {
		String query = "INSERT INTO test.cust(cust_uuid, full_name, identity_num, birth_date, birth_place, "
				+ "address, neighbourhood_num, hamlet_num, village, subdistrict, "
				+ "city, province, zip_code, created_by) VALUES(" + String.join(", ", Collections.nCopies(14, "?"))
				+ ")";
		LOGGER.debug(query);

		Object[] values = new Object[] { customerModel.getCustUuid(), customerModel.getFullName(),
				customerModel.getIdentityNum(), customerModel.getBirthDate(), customerModel.getBirthPlace(),
				customerModel.getAddress(), customerModel.getNeighbourhoodNum(), customerModel.getHamletNum(),
				customerModel.getVillage(), customerModel.getSubdistrict(), customerModel.getCity(),
				customerModel.getProvince(), customerModel.getZipCode(), customerModel.getCreatedBy() };
		LOGGER.debug(Arrays.toString(values));

		return jdbcTemplate.update(query, values);
	}

	public int updateDataById(String custUuid, CustomerModel customerModel)
			throws IllegalArgumentException, IllegalAccessException {
		Object[] queryParam = CommonUtil.queryAndParamUpdateGenerator("test.cust", "cust_uuid", custUuid,
				customerModel);
		String query = (String) queryParam[0];
		LOGGER.debug(query);

		Object[] values = (Object[]) queryParam[1];
		LOGGER.debug(Arrays.toString(values));

		return jdbcTemplate.update(query, values);
	}

}
