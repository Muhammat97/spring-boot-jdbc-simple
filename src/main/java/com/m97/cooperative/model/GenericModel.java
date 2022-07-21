package com.m97.cooperative.model;

public class GenericModel {

	private String code;
	private String args1;
	private String args2;
	private String args3;
	private Object data;

	public GenericModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GenericModel(String code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}

	public GenericModel(String code, Object data, String args1, String args2, String args3) {
		super();
		this.code = code;
		this.data = data;
		this.args1 = args1;
		this.args2 = args2;
		this.args3 = args3;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getArgs1() {
		return args1;
	}

	public void setArgs1(String args1) {
		this.args1 = args1;
	}

	public String getArgs2() {
		return args2;
	}

	public void setArgs2(String args2) {
		this.args2 = args2;
	}

	public String getArgs3() {
		return args3;
	}

	public void setArgs3(String args3) {
		this.args3 = args3;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "GenericModel [code=" + code + ", args1=" + args1 + ", args2=" + args2 + ", args3=" + args3 + ", data="
				+ data + "]";
	}

}
