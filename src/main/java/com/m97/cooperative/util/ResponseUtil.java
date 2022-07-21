package com.m97.cooperative.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.m97.cooperative.model.GenericModel;
import com.m97.cooperative.model.props.MapCollectionProps;
import com.m97.cooperative.model.response.ResponseSchema;

@Component
public class ResponseUtil {

	private static MapCollectionProps mapCollectionProps;

	public static ResponseEntity<Object> setResponse(GenericModel generic) {
		String[] arrMessage = mapCollectionProps.getMessage().getOrDefault(generic.getCode(), "E001").split("\\|");
		int httpStatus = Integer.valueOf(arrMessage[0]);
		String message = arrMessage[1];

		if (generic.getArgs1() != null) {
			message = message.replace("{args1}", generic.getArgs1());
		}

		if (generic.getArgs2() != null) {
			message = message.replace("{args2}", generic.getArgs2());
		}

		if (generic.getArgs3() != null) {
			message = message.replace("{args3}", generic.getArgs3());
		}

		ResponseSchema responseSchema = new ResponseSchema(generic.getCode(), message, generic.getData());

		return new ResponseEntity<Object>(responseSchema, HttpStatus.valueOf(httpStatus));
	}

	@Autowired
	private void setMapCollectionProps(MapCollectionProps mapCollectionProps) {
		ResponseUtil.mapCollectionProps = mapCollectionProps;
	}

}
