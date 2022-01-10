package com.minlia.redis.clock.handler.lock;

import org.aspectj.lang.JoinPoint;
import com.minlia.redis.clock.lock.Lock;
import com.minlia.redis.clock.model.LockInfo;

/**
 * 获取锁超时的处理逻辑接口
 *
 * @author wanglaomo
 * @since 2019/4/15
 **/
public interface LockTimeoutHandler {

    void handle(LockInfo lockInfo, Lock lock, JoinPoint joinPoint);
}
