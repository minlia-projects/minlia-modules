package com.minlia.module.redis.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 封装redis 缓存服务器服务接口
 * Created by garen on 2018/1/25.
 */
@Slf4j
@Service
public class RedisServiceImpl<K, V> implements RedisService<K, V> {

    private final RedisTemplate<K, V> redisTemplate;
    private final ZSetOperations<K, V> zSetOperations;

    public RedisServiceImpl(RedisTemplate<K, V> redisTemplate, ZSetOperations<K, V> zSetOperations) {
        this.redisTemplate = redisTemplate;
        this.zSetOperations = zSetOperations;
    }

    //=============================common============================

    /**
     * @return
     */
    @Override
    public Object ping() {
        return redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }


    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    @Override
    public boolean expire(K key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    @Override
    public long getExpire(K key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    @Override
    public boolean hasKey(K key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @Override
    public void del(K... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Lists.newArrayList(key));
            }
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    @Override
    public Object get(K key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    @Override
    public boolean set(K key, V value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    @Override
    public boolean set(K key, V value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    @Override
    public long incr(K key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    @Override
    public long decr(K key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    @Override
    public Object hget(K key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    @Override
    public Map<Object, Object> hmget(K key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    @Override
    public boolean hmset(K key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    @Override
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

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    @Override
    public boolean hset(K key, String item, V value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    @Override
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

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    @Override
    public void hdel(K key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    @Override
    public boolean hHasKey(K key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    @Override
    public double hincr(K key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    @Override
    public double hdecr(K key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    @Override
    public Set<V> sGet(K key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean sHasKey(K key, V value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long sSet(K key, V... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long sSetAndTime(K key, long time, V... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    @Override
    public long sGetSetSize(K key) {
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
    @Override
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
    @Override
    public boolean zset(K key, V value) {
        long now = System.currentTimeMillis();
        return zSetOperations.add(key, value, now);
    }

    /**
     * 存入一条数据到sorted set
     *
     * @param key
     * @param value
     */
    @Override
    public boolean zset(K key, V value, long scope) {
        return zSetOperations.add(key, value, scope);
    }

    /**
     * 取出整个set的所有记录
     *
     * @param key
     * @return
     */
    @Override
    public Set<V> zgetAll(K key, long expireSec) {
        long now = System.currentTimeMillis();
        long tts = now - expireSec;

        //下标用-1才能表示最大值  score和count要用-inf和+inf
        //return zSetOperations.rangeByScore(key, tts+1, -1);
        return zSetOperations.rangeByScore(key, tts + 1, Long.MAX_VALUE);
    }

    /**
     * 查看匹配数目
     *
     * @param key
     * @param expire:过期时间 单位是秒
     * @return
     */
    @Override
    public long zCount(K key, long expire) {
        long now = System.currentTimeMillis();
        long tts = now - expire;

        //下标用-1才能表示最大值  score和count要用-inf和+inf
        //return zSetOperations.count(key, tts+1, -1);
        return zSetOperations.count(key, tts + 1, Long.MAX_VALUE);
    }

    @Override
    public long zRemove(K key, V object) {
        return zSetOperations.remove(key, object);
    }

    /**
     * 范围删除
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public long zRemoveRange(K key, long start, long end) {
        return zSetOperations.removeRange(key, start, end);
    }

    /**
     * 范围删除
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public long zremoveRangeByScore(K key, long start, long end) {
        return zSetOperations.removeRangeByScore(key, start, end);
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
    @Override
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
    @Override
    public long lGetListSize(K key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    @Override
    public V lGetIndex(K key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    @Override
    public boolean lSet(K key, V value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    @Override
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

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    @Override
    public boolean lSet(K key, List<V> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    @Override
    public boolean lSet(K key, List<V> value, long time) {
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
    @Override
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
    @Override
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