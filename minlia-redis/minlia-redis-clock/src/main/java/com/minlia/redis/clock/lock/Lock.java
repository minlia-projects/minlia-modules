package com.minlia.redis.clock.lock;

/**
 * Created by kl on 2017/12/29.
 */
public interface Lock {

    boolean acquire();

    boolean release();
}

