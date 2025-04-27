package com.example.demo.session;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.session.MapSession;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@DynamoDBTable(tableName = "Sessions")
public class SessionItem {
	private String sessionId;
	private Map<String, Object> attributes;
	private long creationTime;
	private long lastAccessedTime;
	private int maxInactiveInterval;

	public SessionItem() {}
	public SessionItem(MapSession session) {
		this.sessionId = session.getId();
		this.attributes = session.getAttributeNames().stream()
			.collect(Collectors.toMap(name -> name, session::getAttribute));
		this.creationTime = session.getCreationTime().toEpochMilli();
		this.lastAccessedTime = session.getLastAccessedTime().toEpochMilli();
		this.maxInactiveInterval = (int) session.getMaxInactiveInterval().getSeconds();
	}

	@DynamoDBHashKey(attributeName = "sessionId")
	public String getSessionId() { return sessionId; }
	public void setSessionId(String sessionId) { this.sessionId = sessionId; }

	// getters/setters for attributes, creationTime, lastAccessedTime, maxInactiveInterval

	public MapSession toSession() {
		MapSession session = new MapSession(sessionId);
		attributes.forEach(session::setAttribute);
		session.setCreationTime(Instant.ofEpochMilli(creationTime));
		session.setLastAccessedTime(Instant.ofEpochMilli(lastAccessedTime));
		session.setMaxInactiveInterval(Duration.ofSeconds(maxInactiveInterval));
		return session;
	}
}
