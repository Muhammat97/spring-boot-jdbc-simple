package com.m97.cooperative.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.m97.cooperative.util.CommonUtil;
import com.m97.cooperative.util.CustomDateDeserializer;
import com.m97.cooperative.util.CustomDateSerializer;
import com.m97.cooperative.util.CustomTimestampSerializer;

public class CustomerModel {

	@JsonIgnore
	private Integer custId;

	private String custUuid;

	@NotBlank(message = "E006|fullName")
	@Size(min = 3, message = "E007|fullName|{min}")
	@Size(max = 60, message = "E008|fullName|{max}")
	private String fullName;

	@Size(max = 20, message = "E008|identityNum|{max}")
	private String identityNum;

	@Past(message = "E014|birthDate|now")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date birthDate;

	@Size(max = 40, message = "E008|birthPlace|{max}")
	private String birthPlace;

	@Size(max = 250, message = "E008|address|{max}")
	private String address;

	@Pattern(regexp = CommonUtil.REGEX_ONLY_NUMBER, message = "E010|neighbourhoodNum")
	@Size(max = 3, message = "E008|neighbourhoodNum|{max}")
	private String neighbourhoodNum;

	@Pattern(regexp = CommonUtil.REGEX_ONLY_NUMBER, message = "E010|hamletNum")
	@Size(max = 3, message = "E008|hamletNum|{max}")
	private String hamletNum;

	@Size(max = 40, message = "E008|village|{max}")
	private String village;

	@Size(max = 40, message = "E008|subdistrict|{max}")
	private String subdistrict;

	@Size(max = 40, message = "E008|city|{max}")
	private String city;

	@Size(max = 40, message = "E008|province|{max}")
	private String province;

	@Pattern(regexp = CommonUtil.REGEX_ONLY_NUMBER, message = "E010|zipCode")
	@Size(min = 5, max = 5, message = "E009|zipCode|{max}")
	private String zipCode;

	@JsonSerialize(using = CustomTimestampSerializer.class)
	private Timestamp createdAt;

	private String createdBy;

	@JsonSerialize(using = CustomTimestampSerializer.class)
	private Timestamp updatedAt;

	private String updatedBy;

	public CustomerModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getCustUuid() {
		return custUuid;
	}

	public void setCustUuid(String custUuid) {
		this.custUuid = custUuid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdentityNum() {
		return identityNum;
	}

	public void setIdentityNum(String identityNum) {
		this.identityNum = identityNum;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNeighbourhoodNum() {
		return neighbourhoodNum;
	}

	public void setNeighbourhoodNum(String neighbourhoodNum) {
		this.neighbourhoodNum = neighbourhoodNum;
	}

	public String getHamletNum() {
		return hamletNum;
	}

	public void setHamletNum(String hamletNum) {
		this.hamletNum = hamletNum;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getSubdistrict() {
		return subdistrict;
	}

	public void setSubdistrict(String subdistrict) {
		this.subdistrict = subdistrict;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "CustomerModel [custId=" + custId + ", custUuid=" + custUuid + ", fullName=" + fullName
				+ ", identityNum=" + identityNum + ", birthDate=" + birthDate + ", birthPlace=" + birthPlace
				+ ", address=" + address + ", neighbourhoodNum=" + neighbourhoodNum + ", hamletNum=" + hamletNum
				+ ", village=" + village + ", subdistrict=" + subdistrict + ", city=" + city + ", province=" + province
				+ ", zipCode=" + zipCode + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt="
				+ updatedAt + ", updatedBy=" + updatedBy + "]";
	}

}
