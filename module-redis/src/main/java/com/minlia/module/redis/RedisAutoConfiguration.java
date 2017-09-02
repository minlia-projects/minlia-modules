package com.minlia.module.redis;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import redis.embedded.RedisServer;

/**
 * Created by will on 6/19/17.
 * 启动时延迟此BEAN初始化
 */
@Configuration
public class RedisAutoConfiguration {

  /**
   * 当系统启动时自动启动一个指定端口的redis服务器
   */
  public RedisAutoConfiguration() {
//    RedisServer redisServer = null;
//    try {
//      redisServer = new RedisServer(6379);
//    } catch (IOException e) {
//    }
//    redisServer.start();
  }

//  @Bean
//  JedisConnectionFactory jedisConnectionFactory() {
//    return new JedisConnectionFactory();
//  }
//
//  @Bean
//  public RedisTemplate<String, Object> redisTemplate() {
//    final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//    template.setConnectionFactory(jedisConnectionFactory());
//    template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
//    return template;
//  }


}
