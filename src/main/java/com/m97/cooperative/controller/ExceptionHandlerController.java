package com.m97.cooperative.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.m97.cooperative.model.GenericModel;
import com.m97.cooperative.model.exception.CustomRuntimeException;
import com.m97.cooperative.util.ResponseUtil;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (ex.getRootCause().getClass() == CustomRuntimeException.class)
			return responseCustomRuntimeException((CustomRuntimeException) ex.getRootCause());

		return super.handleHttpMessageNotReadable(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String[] defaultMessage = ex.getBindingResult().getFieldError().getDefaultMessage().split("\\|");

		return processErrors(defaultMessage);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	protected ResponseEntity<Object> responseConstraintViolationException(ConstraintViolationException ex) {
		String[] defaultMessage = new String[] { "E001" };
		for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
			defaultMessage = constraintViolation.getMessage().split("\\|");
			if (defaultMessage.length > 0)
				break;
		}

		return processErrors(defaultMessage);
	}

	@ExceptionHandler(value = { CustomRuntimeException.class })
	protected ResponseEntity<Object> responseCustomRuntimeException(CustomRuntimeException ex) {
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
