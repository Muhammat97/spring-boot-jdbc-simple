package com.m97.cooperative.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.m97.cooperative.model.exception.CustomRuntimeException;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
		SimpleDateFormat format = new SimpleDateFormat(CommonUtil.PATTERN_DATE);
		format.setTimeZone(CommonUtil.TIME_ZONE);
		try {
			String date = jsonParser.getText();
			return format.parse(date);
		} catch (Exception e) {
			throw new CustomRuntimeException("E012|date|".concat(CommonUtil.PATTERN_DATE));
		}
	}

}
