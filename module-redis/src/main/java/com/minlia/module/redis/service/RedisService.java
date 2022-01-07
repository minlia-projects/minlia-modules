package com.minlia.module.redis.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 封装redis 缓存服务器服务接口
 * Created by garen on 2018/1/25.
 */
@Slf4j
@Service
public class RedisService<K, V> {

    private final RedisTemplate<K, V> redisTemplate;

    public RedisService(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //=============================common============================

    public Object ping() {
        return redisTemplate.execute((RedisCallback) connection -> connection.ping());
    }

    public boolean expire(K key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public long getExpire(K key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public boolean hasKey(K key) {
        return redisTemplate.hasKey(key);
    }

    public void del(K... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Lists.newArrayList(key));
            }
        }
    }

    //=============================String=============================

    public V get(K key) {
        return Objects.isNull(key) ? null : redisTemplate.opsForValue().get(key);
    }

    public boolean set(K key, V value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("set error key: {0}  value: {1}", key, value);
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(K key, V value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
        return true;
    }

    public long incr(K key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public long decr(K key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    //================================Map=================================

    public Object hget(K key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    public Map<Object, Object> hmget(K key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public boolean hmset(K key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hmset(K key, Map<Object, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hset(K key, String item, V value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hset(K key, String item, V value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void hdel(K key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    public boolean hHasKey(K key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    public double hincr(K key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    public double hdecr(K key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    public Set<V> sGet(K key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean sHasValue(K key, V value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long sSet(K key, V... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public long sSetAndTime(K key, long time, V... values) {
        Long count = this.sSet(key, values);
        if (time > 0) {
            expire(key, time);
        }
        return count;
    }

    public long sSets(K key, Set<V> values) {
        return this.sSetsAndTime(key, 0L, values);
    }

    public long sSetsAndTime(K key, long time, Set<V> values) {
        long count = 0;
        for (V value : values) {
            count += this.sSet(key, value);
        }
        if (time > 0) {
            expire(key, time);
        }
        return count;
    }

    public List<V> sPop(K key, long count) {
        return redisTemplate.opsForSet().pop(key, count);
    }

    /**
     *
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sSize(K key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(K key, V... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //===============================zset=================================

    /**
     * 存入一条数据到sorted set
     *
     * @param key
     * @param value
     */
    public boolean zset(K key, V value) {
        long now = System.currentTimeMillis();
        return redisTemplate.opsForZSet().add(key, value, now);
    }

    /**
     * 存入一条数据到sorted set
     *
     * @param key
     * @param value
     */
    public boolean zset(K key, V value, long scope) {
        return redisTemplate.opsForZSet().add(key, value, scope);
    }

    /**
     * 取出整个set的所有记录
     *
     * @param key
     * @return
     */
    public Set<V> zgetAll(K key, long expireSec) {
        long now = System.currentTimeMillis();
        long tts = now - expireSec;

        //下标用-1才能表示最大值  score和count要用-inf和+inf
        //return zSetOperations.rangeByScore(key, tts+1, -1);
        return redisTemplate.opsForZSet().rangeByScore(key, tts + 1, Long.MAX_VALUE);
    }

    /**
     * 查看匹配数目
     *
     * @param key
     * @param expire:过期时间 单位是秒
     * @return
     */
    public long zCount(K key, long expire) {
        long now = System.currentTimeMillis();
        long tts = now - expire;

        //下标用-1才能表示最大值  score和count要用-inf和+inf
        //return zSetOperations.count(key, tts+1, -1);
        return redisTemplate.opsForZSet().count(key, tts + 1, Long.MAX_VALUE);
    }

    public long zRemove(K key, V object) {
        return redisTemplate.opsForZSet().remove(key, object);
    }

    /**
     * 范围删除
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public long zRemoveRange(K key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 范围删除
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public long zRemoveRangeByScore(K key, long start, long end) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, start, end);
    }


    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<V> lGet(K key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(K key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public V lGetIndex(K key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean lSet(K key, V value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean lSet(K key, V value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean lSetAll(K key, List<V> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean lSetAll(K key, List<V> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(K key, long index, V value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(K key, long count, V value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


//    private static String redisCode = "utf-8";
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    /**
//     * @param keys
//     */
//    public long del(final String... keys) {
//        return redisTemplate.execute(new RedisCallback() {
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                long result = 0;
//                for (int i = 0; i < keys.length; i++) {
//                    result = connection.del(keys[i].getBytes());
//                }
//                return result;
//            }
//        });
//    }
//
//    /**
//     * @param key
//     * @param value
//     * @param liveTime
//     */
//    public void set(final byte[] key, final byte[] value, final long liveTime) {
//        redisTemplate.execute(new RedisCallback() {
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.set(key, value);
//                if (liveTime > 0) {
//                    connection.expire(key, liveTime);
//                }
//                return 1L;
//            }
//        });
//    }
//
//    /**
//     * @param key
//     * @param value
//     * @param liveTime
//     */
//    public void set(K key, String value, long liveTime) {
//        this.set(key.getBytes(), value.getBytes(), liveTime);
//    }
//
//    /**
//     * @param key
//     * @param value
//     */
//    public void set(K key, String value) {
//        this.set(key, value, 0);
//    }
//
//    /**
//     * @param key
//     * @param value
//     */
//    public void set(byte[] key, byte[] value) {
//        this.set(key, value, 0);
//    }
//
//    /**
//     * @param key
//     * @return
//     */
//    public String get(final K key) {
//        return redisTemplate.execute(new RedisCallback() {
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                try {
//                    return new String(connection.get(key.getBytes()), redisCode);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                return "";
//            }
//        });
//    }
//
//    /**
//     * @param pattern
//     * @return
//     */
//    public Setkeys(String pattern) {
//        return redisTemplate.keys(pattern);
//
//    }
//
//    /**
//     * @param key
//     * @return
//     */
//    public boolean exists(final K key) {
//        return redisTemplate.execute(new RedisCallback() {
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                return connection.exists(key.getBytes());
//            }
//        });
//    }
//
//    /**
//     * @return
//     */
//    public String flushDB() {
//        return redisTemplate.execute(new RedisCallback() {
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.flushDb();
//                return "ok";
//            }
//        });
//    }
//
//    /**
//     * @return
//     */
//    public long dbSize() {
//        return redisTemplate.execute(new RedisCallback() {
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                return connection.dbSize();
//            }
//        });
//    }
//

//    private RedisServiceImpl() {
//
//    }


}