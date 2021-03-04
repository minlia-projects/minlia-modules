package com.minlia.module.redis.util;

import com.minlia.cloud.holder.ContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

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
public class RedisUtils {

    private static RedisTemplate<String, Object> redisTemplate;

    private static ZSetOperations<String, Object> zSetOperations;

    public static RedisTemplate getRedisTemplate() {
        if (null == redisTemplate) {
            redisTemplate = ContextHolder.getContext().getBean(RedisTemplate.class);
        }
        return redisTemplate;
    }

    public static ZSetOperations getZSetOperations() {
        if (null == zSetOperations) {
            zSetOperations = ContextHolder.getContext().getBean(ZSetOperations.class);
        }
        return zSetOperations;
    }


    //=============================common============================

    /**
     * @return
     */
    public static Object ping() {
        return getRedisTemplate().execute(new RedisCallback() {
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
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                getRedisTemplate().expire(key, time, TimeUnit.SECONDS);
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
    public static long getExpire(String key) {
        return getRedisTemplate().getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return getRedisTemplate().hasKey(key);
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
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                getRedisTemplate().delete(key[0]);
            } else {
                getRedisTemplate().delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    public static void delByPrefix(String prefix) {
        Set<String> keys = getRedisTemplate().keys(prefix + "*");
        redisTemplate.delete(keys);
    }

    public static void delBySuffix(String suffix) {
        Set<String> keys = getRedisTemplate().keys("*" + suffix);
        redisTemplate.delete(keys);
    }


    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return key == null ? null : getRedisTemplate().opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        try {
            getRedisTemplate().opsForValue().set(key, value);
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
    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                getRedisTemplate().opsForValue().set(key, value, time, TimeUnit.SECONDS);
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
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return getRedisTemplate().opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return getRedisTemplate().opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hget(String key, String item) {
        return getRedisTemplate().opsForHash().get(key, item);
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static <T> T hget(String key, String item, Class<T> c) {
        Object o = getRedisTemplate().opsForHash().get(key, item);
        return Objects.nonNull(o) ? c.cast(o) : null;
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        return getRedisTemplate().opsForHash().entries(key);
    }

//    /**
//     * 获取hashKey对应的所有键值
//     *
//     * @param key 键
//     * @return 对应的多个键值
//     */
//    public static <T> T  hmget(String key, Class<T> c) {
//        Map<Object, Object> o = getRedisTemplate().opsForHash().entries(key);
//        return getRedisTemplate().opsForHash().entries(key);
//    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean hmset(String key, Map<String, Object> map) {
        try {
            getRedisTemplate().opsForHash().putAll(key, map);
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
    public static boolean hmset(String key, Map<Object, Object> map, long time) {
        try {
            getRedisTemplate().opsForHash().putAll(key, map);
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
    public static boolean hset(String key, String item, Object value) {
        try {
            getRedisTemplate().opsForHash().put(key, item, value);
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
    public static boolean hset(String key, String item, Object value, long time) {
        try {
            getRedisTemplate().opsForHash().put(key, item, value);
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
    public static void hdel(String key, Object... item) {
        getRedisTemplate().opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        return getRedisTemplate().opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static Long hincr(String key, String item, long by) {
        return getRedisTemplate().opsForHash().increment(key, item, by);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static double hincr(String key, String item, double by) {
        return getRedisTemplate().opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public static double hdecr(String key, String item, double by) {
        return getRedisTemplate().opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key) {
        try {
            return getRedisTemplate().opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasValue(String key, Object value) {
        try {
            return getRedisTemplate().opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        try {
            return getRedisTemplate().opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = getRedisTemplate().opsForSet().add(key, values);
            if (time > 0) expire(key, time);
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
    public static long sGetSetSize(String key) {
        try {
            return getRedisTemplate().opsForSet().size(key);
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
    public static long setRemove(String key, Object... values) {
        try {
            Long count = getRedisTemplate().opsForSet().remove(key, values);
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
    public static boolean zset(String key, Object value) {
        long now = System.currentTimeMillis();
        return getZSetOperations().add(key, value, now);
    }

    /**
     * 存入一条数据到sorted set
     *
     * @param key
     * @param value
     */
    public static boolean zset(String key, Object value, long scope) {
        return getZSetOperations().add(key, value, scope);
    }

    /**
     * 正序获取
     *
     * @param key
     * @return
     */
    public static Set<Object> zrange(String key, long start, long end) {
        return getZSetOperations().rangeByScore(key, start, end);
    }

    /**
     * 倒序获取
     *
     * @param key
     * @return
     */
    public static Set<Object> reverseRange(String key, long start, long end) {
        return getZSetOperations().rangeByScore(key, start, end);
    }

    /**
     * 取出整个set的所有记录
     *
     * @param key
     * @return
     */
    public static Set<Object> zgetAll(String key, long expireSec) {
        long now = System.currentTimeMillis();
        long tts = now - expireSec;

        //下标用-1才能表示最大值  score和count要用-inf和+inf
        //return getZSetOperations().rangeByScore(key, tts+1, -1);
        return getZSetOperations().rangeByScore(key, tts + 1, Long.MAX_VALUE);
    }

    /**
     * 查看匹配数目
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static long zCount(String key, long start, long end) {
        return getZSetOperations().count(key, start, end);
    }

    /**
     * 查看匹配数目
     *
     * @param key
     * @param expire:过期时间 单位是秒
     * @return
     */
    public static long zCount(String key, long expire) {
        long now = System.currentTimeMillis();
        long tts = now - expire;

        //下标用-1才能表示最大值  score和count要用-inf和+inf
        //return getZSetOperations().count(key, tts+1, -1);
        return getZSetOperations().count(key, tts + 1, Long.MAX_VALUE);
    }

    public static long zRemove(String key, Object object) {
        return getZSetOperations().remove(key, object);
    }

    /**
     * 范围删除
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static long zRemoveRange(String key, long start, long end) {
        return getZSetOperations().removeRange(key, start, end);
    }

    /**
     * 范围删除
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static long zremoveRangeByScore(String key, long start, long end) {
        return getZSetOperations().removeRangeByScore(key, start, end);
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
    public static List<Object> lGet(String key, long start, long end) {
        try {
            return getRedisTemplate().opsForList().range(key, start, end);
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
    public static long lGetListSize(String key) {
        try {
            return getRedisTemplate().opsForList().size(key);
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
    public static Object lGetIndex(String key, long index) {
        try {
            return getRedisTemplate().opsForList().index(key, index);
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
    public static boolean lSet(String key, Object value) {
        try {
            getRedisTemplate().opsForList().rightPush(key, value);
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
    public static boolean lSet(String key, Object value, long time) {
        try {
            getRedisTemplate().opsForList().rightPush(key, value);
            if (time > 0) expire(key, time);
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
    public static boolean lSet(String key, List<Object> value) {
        try {
            getRedisTemplate().opsForList().rightPushAll(key, value);
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
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            getRedisTemplate().opsForList().rightPushAll(key, value);
            if (time > 0) expire(key, time);
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
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            getRedisTemplate().opsForList().set(key, index, value);
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
    public static long lRemove(String key, long count, Object value) {
        try {
            Long remove = getRedisTemplate().opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


//    private static String redisCode = "utf-8";
//
//    @Autowired
//    private RedisTemplate<String, String> getRedisTemplate();
//
//    /**
//     * @param keys
//     */
//    public static long del(final String... keys) {
//        return getRedisTemplate().execute(new RedisCallback() {
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
//    public static void set(final byte[] key, final byte[] value, final long liveTime) {
//        getRedisTemplate().execute(new RedisCallback() {
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
//    public static void set(String key, String value, long liveTime) {
//        this.set(key.getBytes(), value.getBytes(), liveTime);
//    }
//
//    /**
//     * @param key
//     * @param value
//     */
//    public static void set(String key, String value) {
//        this.set(key, value, 0);
//    }
//
//    /**
//     * @param key
//     * @param value
//     */
//    public static void set(byte[] key, byte[] value) {
//        this.set(key, value, 0);
//    }
//
//    /**
//     * @param key
//     * @return
//     */
//    public static String get(final String key) {
//        return getRedisTemplate().execute(new RedisCallback() {
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
//    public static Setkeys(String pattern) {
//        return getRedisTemplate().keys(pattern);
//
//    }
//
//    /**
//     * @param key
//     * @return
//     */
//    public static boolean exists(final String key) {
//        return getRedisTemplate().execute(new RedisCallback() {
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                return connection.exists(key.getBytes());
//            }
//        });
//    }
//
//    /**
//     * @return
//     */
//    public static String flushDB() {
//        return getRedisTemplate().execute(new RedisCallback() {
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
//    public static long dbSize() {
//        return getRedisTemplate().execute(new RedisCallback() {
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