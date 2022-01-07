package com.minlia.module.redis.listener;

import com.minlia.module.redis.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisInitListner implements ApplicationRunner {

    private final RedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RedisUtils.redisTemplate = redisTemplate;
    }

}