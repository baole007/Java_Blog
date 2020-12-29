package com.jdxl.seedcourse.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.cloud.core.constant.MsgInfoEnum;
import com.cloud.core.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;
import static org.springframework.util.StringUtils.split;

@Configuration
//@EnableCaching
@EnableConfigurationProperties(RedisConfigProperties.class)
public class RedisConfig {

    private RedisConfigProperties redisConfigProperties;

    @Autowired
    public void setRedisConfigFile(RedisConfigProperties redisConfigProperties) {
        this.redisConfigProperties = redisConfigProperties;
    }

    /**
     * 配置RedisConnecionFactory
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {

        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(jedisPoolConfig)
                .and()
                .readTimeout(redisConfigProperties.getReadTimeout())
                .connectTimeout(redisConfigProperties.getConnectTimeout())
                .build();

        Set<String> hostAndPorts = StringUtils.commaDelimitedListToSet(redisConfigProperties.getNodes());

        if (hostAndPorts.size() > 1) {
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
            for (String hostAndPort : hostAndPorts) {
                redisClusterConfiguration.addClusterNode(readHostAndPortFromString(hostAndPort));
            }
            return new JedisConnectionFactory(redisClusterConfiguration, jedisClientConfiguration);
        } else if (hostAndPorts.size() == 1) {

            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            String[] hostAndPort = hostAndPorts.toArray(new String[]{})[0].split(":");
            redisStandaloneConfiguration.setDatabase(redisConfigProperties.getDatabase());
            redisStandaloneConfiguration.setHostName(hostAndPort[0]);
            redisStandaloneConfiguration.setPort(Integer.parseInt(hostAndPort[1]));

            return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
        }

        throw new SystemException(MsgInfoEnum.CUSTOM_SYSTEM_ERROR, "RedisConnectionFactory build error!");
    }

    private RedisNode readHostAndPortFromString(String hostAndPort) {
        String[] args = split(hostAndPort, ":");
        notNull(args, "HostAndPort need to be seperated by ':'.");
        isTrue(args.length == 2, "Host and Port String needs to specified as host:port");
        return new RedisNode(args[0], Integer.valueOf(args[1]));
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxIdle(redisConfigProperties.getPool().getMaxIdle());
        jedisPoolConfig.setMinIdle(redisConfigProperties.getPool().getMinIdle());
        jedisPoolConfig.setMaxTotal(redisConfigProperties.getPool().getMaxActive());
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
        jedisPoolConfig.setMaxWaitMillis(redisConfigProperties.getPool().getMaxWait().toMillis());

        jedisPoolConfig.setTestOnBorrow(redisConfigProperties.getPool().isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(redisConfigProperties.getPool().isTestOnReturn());
        jedisPoolConfig.setTestWhileIdle(redisConfigProperties.getPool().isTestWhileIdle());

        return jedisPoolConfig;
    }

    /**
     * RedisTemplate
     *
     * @param redisConnectionFactory 链接工厂
     * @param jackson2JsonRedisSerializer value的序列化
     * @return Spring默认的RedisTemplate
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                       Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        RedisTemplate template = new RedisTemplate(redisConnectionFactory);
        //定义key序列化方式
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        template.setKeySerializer(redisSerializer);
        template.setHashKeySerializer(redisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 缓存管理器 CacheManager 可使用@cacheable
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
                                     Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration defaultConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(redisConfigProperties.getDefaultTimeToLive())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        //初始化RedisCacheManager
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(defaultConfiguration);

        Map<String, RedisCacheConfiguration> map = Maps.newHashMap();
        Optional.ofNullable(redisConfigProperties.getCustomCacheConfig())
                .ifPresent(customCache -> customCache.forEach((key, redisProperties) -> {
                    RedisCacheConfiguration customCfg = handleRedisCacheConfiguration(redisProperties, defaultConfiguration);
                    map.put(key, customCfg);
                }));

        builder.withInitialCacheConfigurations(map);
        return builder.build();

    }

    private RedisCacheConfiguration handleRedisCacheConfiguration(CacheProperties.Redis redisProperties,
                                                                  RedisCacheConfiguration config) {
        if (Objects.isNull(redisProperties)) {
            return config;
        }
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.computePrefixWith(cacheName -> cacheName + redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }


    /**
     * 配置Jackson2JsonRedisSerializer序列化器, 将对象序列化为字符串存储, 和将对象反序列化为对象
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        //设置CacheManager的value的序列化方式为json序列化
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // 解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(om);
        return serializer;
    }

}
