package com.m97.cooperative.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.m97.cooperative.model.GenericModel;
import com.m97.cooperative.model.exception.CustomRuntimeException;
import com.m97.cooperative.util.ResponseUtil;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<Object> responseConstraintViolationException(ConstraintViolationException ex) {
		String[] defaultMessage = new String[] { "E001" };
		for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
			defaultMessage = constraintViolation.getMessage().split("\\|");
			if (defaultMessage.length > 0)
				break;
		}

		return processErrors(defaultMessage);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<Object> responseMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		String[] defaultMessage = ex.getBindingResult().getFieldError().getDefaultMessage().split("\\|");

		return processErrors(defaultMessage);
	}

	@ExceptionHandler(value = { CustomRuntimeException.class })
	public ResponseEntity<Object> responseRuntimeException(CustomRuntimeException ex) {
		String[] defaultMessage = ex.getMessage().split("\\|");

		return processErrors(defaultMessage);
	}

	public ResponseEntity<Object> processErrors(String[] defaultMessage) {
		String code = defaultMessage.length > 0 ? defaultMessage[0] : "E001";
		String args1 = defaultMessage.length > 1 ? defaultMessage[1] : null;
		String args2 = defaultMessage.length > 2 ? defaultMessage[2] : null;
		String args3 = defaultMessage.length > 3 ? defaultMessage[3] : null;

		GenericModel generic = new GenericModel(code, null, args1, args2, args3);

		return ResponseUtil.setResponse(generic);
	}

}
