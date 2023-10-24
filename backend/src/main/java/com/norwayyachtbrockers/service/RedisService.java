package com.norwayyachtbrockers.service;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RedisService {

    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    public RedisService(ReactiveRedisTemplate<String, Object> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    public Mono<Boolean> set(String key, Object value) {
        return reactiveRedisTemplate.opsForValue().set(key, value);
    }

    public Mono<Object> get(String key) {
        return reactiveRedisTemplate.opsForValue().get(key);
    }

    // Add other CRUD methods as needed.
}
