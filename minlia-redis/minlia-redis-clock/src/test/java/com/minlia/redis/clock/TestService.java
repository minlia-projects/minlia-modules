package com.minlia.redis.clock;

import com.minlia.redis.clock.annotation.Klock;
import com.minlia.redis.clock.annotation.KlockKey;
import com.minlia.redis.clock.model.LockTimeoutStrategy;
import org.springframework.stereotype.Service;

/**
 * Created by kl on 2017/12/29.
 */
@Service
public class TestService {

    int i = 0;

    @Klock(waitTime = 10,leaseTime = 10,keys = {"#param","222222","333333"},lockTimeoutStrategy = LockTimeoutStrategy.FAIL_FAST)
    public String getValue(String param) throws Exception {
        if ("sleep".equals(param)) {//线程休眠或者断点阻塞，达到一直占用锁的测试效果
            Thread.sleep(1000*20);
        }
        System.out.println(i++);
        return "success";
    }

    @Klock(keys = {"#userId"})
    public String getValue(String userId,@KlockKey Integer id)throws Exception{
        Thread.sleep(60*1000);
        return "success";
    }

    @Klock(keys = {"#user.name","#user.id"})
    public String getValue(User user)throws Exception{
        Thread.sleep(60*1000);
        return "success";
    }

}
