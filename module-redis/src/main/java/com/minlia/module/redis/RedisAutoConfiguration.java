package com.minlia.module.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 86400秒 = 1天
 */
@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.cache.redis")
@Order(value = Ordered.HIGHEST_PRECEDENCE)
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400 * 30)
public class RedisAutoConfiguration extends CachingConfigurerSupport {

    private static String SYMBOL_UNDERLINE = "_";

    /**
     * 默认键值生成器：类名+方法名+参数
     * Cacheable中设置 key="xx"后按此生成
     *
     * @return
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            //格式化缓存key字符串
            StringBuilder sb = new StringBuilder();
            //追加类名
            sb.append(target.getClass().getSimpleName());
            sb.append(SYMBOL_UNDERLINE);
            //追加方法名
            sb.append(method.getName());
            //遍历参数并且追加
            for (Object obj : params) {
                sb.append(SYMBOL_UNDERLINE);
                sb.append(ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE));
            }
            System.out.println("调用Redis缓存Key : " + sb.toString());
            return sb.toString();
        };
    }

    /**
     * 缓存管理器
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @Primary
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        //解决查询缓存转换异常的问题
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))   // 设置缓存管理器管理的缓存的默认过期时间
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();        // 不缓存空值

        // 初始化缓存名称
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("my-redis-cache1");
        cacheNames.add("my-redis-cache2");

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("my-redis-cache1", redisCacheConfiguration.entryTtl(Duration.ofMinutes(40)));
        configMap.put("my-redis-cache2", redisCacheConfiguration.entryTtl(Duration.ofMinutes(50)));

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }

    @SuppressWarnings("ALL")
    @Bean
    @Primary
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @Primary
    @ConditionalOnBean(value = {RedisTemplate.class})
    public ZSetOperations zSetOperations(RedisTemplate redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Override
    @Bean
    public CacheErrorHandler errorHandler() {
        return new RedisCacheErrorHandler();
    }

    @Slf4j
    private static class RedisCacheErrorHandler extends SimpleCacheErrorHandler {
        @Override
        public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
            log.error("handleCacheGetError key = {}, value = {}", key, cache);
            log.error("cache get error", exception);
        }

        @Override
        public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
            log.error("handleCachePutError key = {}, value = {}", key, cache);
            log.error("cache put error", exception);
        }

        @Override
        public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
            log.error("handleCacheEvictError key = {}, value = {}", key, cache);
            log.error("cache evict error", exception);
        }

        @Override
        public void handleCacheClearError(RuntimeException exception, Cache cache) {
            log.error("handleCacheClearError value = {}", cache);
            log.error("cache clear error", exception);
        }
    }

}
