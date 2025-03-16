package com.arom.polisee.global.login.service;

import com.arom.polisee.global.login.dto.CustomUserDetails;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisCacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final long CACHE_EXPIRE_TIME = 30; // 30분
    private static final String USER_KEY_PREFIX = "USER:";

    public void saveUserDetails(CustomUserDetails customUserDetails) {
        String key = getUserKey(customUserDetails.getId());
        redisTemplate.opsForValue().set(key + customUserDetails.getId(), customUserDetails, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    public CustomUserDetails getUserDetails(Long userId) {
        String key = getUserKey(userId);
        Object data = redisTemplate.opsForValue().get(key);
        if (data == null) {
            return null;
        }
        //  LinkedHashMap을 CustomUserDetails로 변환
        return objectMapper.convertValue(data, CustomUserDetails.class);
    }

    private String getUserKey(Long userId) {
        return String.format("%s%d", USER_KEY_PREFIX, userId);
    }
    public void deleteUserDetails(Long userId) {
        redisTemplate.delete("USER_" + userId);
    }
}