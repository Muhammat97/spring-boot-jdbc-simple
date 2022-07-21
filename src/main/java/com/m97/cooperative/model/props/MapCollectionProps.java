package com.m97.cooperative.model.props;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("map-collection")
public class MapCollectionProps {

	private Map<String, String> message = new HashMap<>();

	private Map<String, String> transactionType = new HashMap<>();

	public MapCollectionProps() {
		super();
		transactionType.put("DEPOSIT", "SAVINGS");
		transactionType.put("WITHDRAWAL", "SAVINGS");
		transactionType.put("LOAN", "LOAN");
		transactionType.put("REFUND", "LOAN");
	}

	public Map<String, String> getMessage() {
		return message;
	}

	public void setMessage(Map<String, String> message) {
		this.message = message;
	}

	public Map<String, String> getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Map<String, String> transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "MapCollectionProps [message=" + message + ", transactionType=" + transactionType + "]";
	}

}
