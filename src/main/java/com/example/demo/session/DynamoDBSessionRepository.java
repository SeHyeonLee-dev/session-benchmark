package com.example.demo.session;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;

import java.time.Duration;

public class DynamoDBSessionRepository implements SessionRepository<MapSession> {
	private final DynamoDBMapper mapper;

	public DynamoDBSessionRepository(AmazonDynamoDB client, DynamoDBMapper mapper) {
		this.mapper = mapper;
		// TODO: 테이블 생성 로직 추가
	}

	@Override
	public MapSession createSession() {
		MapSession session = new MapSession();
		session.setMaxInactiveInterval(Duration.ofMinutes(30));
		return session;
	}

	@Override
	public void save(MapSession session) {
		SessionItem item = new SessionItem(session);
		mapper.save(item);
	}

	@Override
	public MapSession findById(String id) {
		SessionItem item = mapper.load(SessionItem.class, id);
		return (item != null) ? item.toSession() : null;
	}

	@Override
	public void deleteById(String id) {
		SessionItem item = new SessionItem();
		item.setSessionId(id);
		mapper.delete(item);
	}
}
