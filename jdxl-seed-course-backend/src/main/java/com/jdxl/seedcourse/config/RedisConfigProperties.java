package com.jdxl.seedcourse.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "jdxl.redis")
public class RedisConfigProperties {

    private String nodes;
    private int database;
    private Duration defaultTimeToLive;
    private Duration readTimeout;
    private Duration connectTimeout;

    private PoolConfig pool;

    private Map<String, CacheProperties.Redis> customCacheConfig;

    @Data
    public static class PoolConfig extends RedisProperties.Pool {
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private boolean testWhileIdle;
    }

}
