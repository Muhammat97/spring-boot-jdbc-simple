package com.m97.cooperative.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomTimestampSerializer extends JsonSerializer<Timestamp> {

	@Override
	public void serialize(Timestamp value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		SimpleDateFormat format = new SimpleDateFormat(CommonUtil.PATTERN_TIMESTAMP);
		format.setTimeZone(CommonUtil.TIME_ZONE);

		jsonGenerator.writeString(format.format(value));
	}

}
