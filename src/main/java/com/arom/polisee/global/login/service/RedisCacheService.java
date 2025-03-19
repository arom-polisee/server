package com.arom.polisee.global.login.service;

import com.arom.polisee.global.login.dto.CustomUserDetails;
import com.arom.polisee.global.login.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final long CACHE_EXPIRE_TIME = 30; // 30분
    private static final String USER_KEY_PREFIX = "USER:";

    public void saveUserDetails(UserDto userDto) {
        String key = getUserKey(userDto.getId());

        try {
            log.info("Redis 저장 Key: {}, Value: {}", key, objectMapper.writeValueAsString(userDto));
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 오류: {}", e.getMessage());
        }

        redisTemplate.opsForValue().set(key, userDto, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    public UserDto getUserDetails(Long userId) {
        String key = getUserKey(userId);
        Object data = redisTemplate.opsForValue().get(key);
        if (data == null) {
            return null;
        }
        return objectMapper.convertValue(data, UserDto.class);
    }

    private String getUserKey(Long userId) {
        return String.format("%s%d", USER_KEY_PREFIX, userId);
    }
    public void deleteUserDetails(Long userId) {
        redisTemplate.delete(USER_KEY_PREFIX + userId);
    }
}