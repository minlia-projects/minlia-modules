package com.minlia.module.redis;

import com.minlia.module.redis.service.RedisService;
import com.minlia.module.redis.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        redisService.del("test");
        redisService.set("test", "但撒范德萨发");
        redisService.set("test", new DemoEntity(111, "aaa", "但撒发生大"));
        DemoEntity value = (DemoEntity) redisService.get("test");
        System.out.println(value.getAaa());
    }

    @Test
    void sSet() {
        Set<DemoEntity> values = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            values.add(new DemoEntity(i, "aaaaa", "但撒发生大"));
        }
        RedisUtils.sSetsAndTime("test:set", 100, values);
    }

    @Test
    void sPop() {
        List<DemoEntity> list = redisService.sPop("test:set", 3);
        for (DemoEntity s : list) {
            System.out.println(s);
        }
    }

}