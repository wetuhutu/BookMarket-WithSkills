package com.bookmarket.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public Boolean expire(String key, Long seconds) {
        return stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    public Long increase(String key, Long value) {
        return stringRedisTemplate.opsForValue().increment(key, value);
    }

    public Double increaseScore(String key, String member, Long increasement) {
        return stringRedisTemplate.opsForZSet().incrementScore(key,member,increasement);
    }

    public Set<String> getRevMemberRange(String key,Long start,Long end) {
        return stringRedisTemplate.opsForZSet().reverseRange(key,start,end);
    }

}
