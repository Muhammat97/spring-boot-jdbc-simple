package com.m97.cooperative.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSchema {

	private String code;
	private String message;
	private Object output;

	public ResponseSchema() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseSchema(String code, String message, Object output) {
		super();
		this.code = code;
		this.message = message;
		this.output = output;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "ResponseSchema [code=" + code + ", message=" + message + ", output=" + output + "]";
	}

}
