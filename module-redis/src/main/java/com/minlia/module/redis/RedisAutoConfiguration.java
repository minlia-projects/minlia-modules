package com.minlia.module.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 86400秒 = 1天
 */
@Configuration
@EnableScheduling
@EnableCaching
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400 * 30)
public class RedisAutoConfiguration extends CachingConfigurerSupport {

//    @SuppressWarnings("SpringJavaAutowiringInspection")
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate(redisConnectionFactory);
//        StringRedisSerializer redisSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(redisSerializer);
//        redisTemplate.setValueSerializer(redisSerializer);
//        redisTemplate.setHashValueSerializer(redisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

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
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName());
            sb.append(SYMBOL_UNDERLINE);
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(SYMBOL_UNDERLINE);
                sb.append(ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE));
            }
            return sb.toString();
        };
    }

    @Bean
    @ConditionalOnBean(value = {RedisTemplate.class})
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisSerializer keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        //按cacheName设置缓存过期时间
//        Map<String, Long> expires = new HashMap<String, Long>();
//        expires.put("news", 60L);
//        cacheManager.setExpires(expires);

        //设置缓存默认过期时间:秒
        cacheManager.setDefaultExpiration(60 * 30);
        return cacheManager;
    }

    @SuppressWarnings("ALL")
    @Bean
    @Primary
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        this.setSerializer(redisTemplate);
        return redisTemplate;
    }

    private void setSerializer(RedisTemplate<String, String> redisTemplate) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
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
