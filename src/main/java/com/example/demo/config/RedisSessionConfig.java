package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@ConditionalOnProperty(name = "session.store", havingValue = "redis", matchIfMissing = true)
@EnableRedisHttpSession
public class RedisSessionConfig {
	// Enables Redis-backed HTTP sessions
}
