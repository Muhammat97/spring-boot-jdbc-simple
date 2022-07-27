package com.m97.cooperative.util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.m97.cooperative.util.annotation.ColumnName;

public class CommonUtil {

	public static final String REGEX_ONLY_NUMBER = "^[0-9]+$";

	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss";

	public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+7");

	@SuppressWarnings("unchecked")
	public static Object[] queryAndParamUpdateGenerator(String tableName, String nameUpdateBy, Object valueUpdateBy,
			Object model) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder query = new StringBuilder();
		List<Object> param = new LinkedList<>();

		query.append(String.format("UPDATE %s SET ", tableName));

		if (model instanceof Map) {
			Iterator<Map.Entry<String, Object>> iterator = ((Map<String, Object>) model).entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				if (entry.getValue() != null) {
					query.append(String.format("%s = ?, ", entry.getKey()));
					param.add(entry.getValue());
				}
			}
		} else {
			Field[] fields = model.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.get(model) != null) {
					String fieldName = field.getName();
					ColumnName tableColumnName = field.getDeclaredAnnotation(ColumnName.class);
					if (tableColumnName != null)
						fieldName = tableColumnName.value();
					query.append(String.format("%s = ?, ", fieldName));
					param.add(field.get(model));
				}
			}
		}
		query.append("updated_at = NOW() ");

		query.append(String.format("WHERE %s = ?", nameUpdateBy));
		param.add(valueUpdateBy);

		return new Object[] { query.toString(), param.toArray() };
	}

}
