package com.example.demo.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.demo.session.DynamoDBSessionRepository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.SessionRepository;
import org.springframework.session.MapSession;

@Configuration
@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "dynamodb")
public class DynamoDBSessionConfig {

	@Bean
	public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
		return new AwsClientBuilder.EndpointConfiguration(
			"${aws.dynamodb.endpoint}", "${aws.dynamodb.region}");
	}

	@Bean
	public AmazonDynamoDB amazonDynamoDB(AwsClientBuilder.EndpointConfiguration cfg) {
		return AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(cfg)
			.withCredentials(new DefaultAWSCredentialsProviderChain())
			.build();
	}

	@Bean
	public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB client) {
		return new DynamoDBMapper(client);
	}

	@Bean
	public SessionRepository<MapSession> sessionRepository(AmazonDynamoDB client, DynamoDBMapper mapper) {
		return new DynamoDBSessionRepository(client, mapper);
	}
}
