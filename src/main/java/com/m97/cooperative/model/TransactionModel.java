package com.m97.cooperative.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.m97.cooperative.util.CustomTimestampSerializer;

public class TransactionModel {

	private String tranUuid;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String fullName;

	@JsonIgnore
	private Integer custAcctId;

	@NotBlank(message = "E006|tranType")
	@Pattern(regexp = "DEPOSIT|WITHDRAWAL|LOAN|REFUND", message = "E011|tranType|DEPOSIT or WITHDRAWAL or LOAN or REFUND")
	private String tranType;

	@NotNull(message = "E006|tranAmount")
	@Min(value = 1, message = "E013|tranAmount|{value}|90000000000000000")
	@Max(value = 90000000000000000L, message = "E013|tranAmount|1|{value}")
	private BigDecimal tranAmount;

	@JsonSerialize(using = CustomTimestampSerializer.class)
	private Timestamp tranDate;

	private String createdBy;

	public TransactionModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTranUuid() {
		return tranUuid;
	}

	public void setTranUuid(String tranUuid) {
		this.tranUuid = tranUuid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getCustAcctId() {
		return custAcctId;
	}

	public void setCustAcctId(Integer custAcctId) {
		this.custAcctId = custAcctId;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public BigDecimal getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(BigDecimal tranAmount) {
		this.tranAmount = tranAmount;
	}

	public Timestamp getTranDate() {
		return tranDate;
	}

	public void setTranDate(Timestamp tranDate) {
		this.tranDate = tranDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "TransactionModel [tranUuid=" + tranUuid + ", fullName=" + fullName + ", custAcctId=" + custAcctId
				+ ", tranType=" + tranType + ", tranAmount=" + tranAmount + ", tranDate=" + tranDate + ", createdBy="
				+ createdBy + "]";
	}

}
