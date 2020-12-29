package com.jdxl.seedcourse.config;


import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 这是 StringRedisTemplate 的封装类
 *
 * 不建议去复写 StringRedisTemplate 的方法 除非你非常确定这样做是正确的
 * 使用时均使用 RedisTemplate 中的方法
 *
 */
public class RedisTemplate extends StringRedisTemplate {
    public RedisTemplate(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }
}
