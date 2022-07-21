package com.m97.cooperative.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		SimpleDateFormat format = new SimpleDateFormat(CommonUtil.PATTERN_DATE);
		format.setTimeZone(CommonUtil.TIME_ZONE);

		jsonGenerator.writeString(format.format(value));
	}

}
