package com.m97.cooperative.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CustomerAccountModel {

	@JsonIgnore
	private Integer custAcctId;

	private String custUuid;

	private String acctName;

	private String currency;

	private BigDecimal balance = new BigDecimal(0);

	private String createdBy;

	private String updatedBy;

	public CustomerAccountModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getCustAcctId() {
		return custAcctId;
	}

	public void setCustAcctId(Integer custAcctId) {
		this.custAcctId = custAcctId;
	}

	public String getCustUuid() {
		return custUuid;
	}

	public void setCustUuid(String custUuid) {
		this.custUuid = custUuid;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "CustomerAccountModel [custAcctId=" + custAcctId + ", custUuid=" + custUuid + ", acctName=" + acctName
				+ ", currency=" + currency + ", balance=" + balance + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + "]";
	}

}
