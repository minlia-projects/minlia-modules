package com.minlia.module.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 的操作开放接口
 * Created by garen on 2018/1/25.
 */
public interface RedisService<K, V> {

    //=============================common============================

    /**
     * 检查是否连接成功
     *
     * @return
     */
    Object ping();

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    boolean expire(K key, long time);

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    long getExpire(K key);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    boolean hasKey(K key);

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    void del(K... key);

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    Object get(K key);

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    boolean set(K key, V value);

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    boolean set(K key, V value, long time);

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    long incr(K key, long delta);

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    long decr(K key, long delta);

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    Object hget(K key, String item);

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    Map<Object, Object> hmget(K key);

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    boolean hmset(K key, Map<String, Object> map);

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    boolean hmset(K key, Map<Object, Object> map, long time);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    boolean hset(K key, String item, V value);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    boolean hset(K key, String item, V value, long time);

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    void hdel(K key, Object... item);

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    boolean hHasKey(K key, String item);

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    double hincr(K key, String item, double by);

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    double hdecr(K key, String item, double by);

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    Set<V> sGet(K key);

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    boolean sHasKey(K key, V value);

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    long sSet(K key, V... values);

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    long sSetAndTime(K key, long time, V... values);

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    long sGetSetSize(K key);

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    long setRemove(K key, V... values);


    //===============================sorted set===========================

    /**
     * 存入一条数据到sorted set
     *
     * @param key
     * @param value
     */
    boolean zset(K key, V value);

    boolean zset(K key, V value, long scope);

    Set<V> zgetAll(K key, long expireSec);

    long zCount(K key, long expire);

    /**
     * 范围删除
     *
     * @param key
     * @param object
     * @return
     */
    long zRemove(K key, V object);

    long zRemoveRange(K key, long start, long end);

    long zremoveRangeByScore(K key, long start, long end);

    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    List<V> lGet(K key, long start, long end);

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    long lGetListSize(K key);

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    V lGetIndex(K key, long index);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    boolean lSet(K key, V value);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    boolean lSet(K key, V value, long time);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    boolean lSet(K key, List<V> value);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    boolean lSet(K key, List<V> value, long time);

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    boolean lUpdateIndex(K key, long index, V value);

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    long lRemove(K key, long count, V value);


//    /**
//     * 通过key删除
//     *
//     * @param keys
//     */
//    public abstract long del(String... keys);
//
//    /**
//     * 添加key value 并且设置存活时间(byte)
//     *
//     * @param key
//     * @param value
//     * @param liveTime
//     */
//    public abstract void set(byte[] key, byte[] value, long liveTime);
//
//    /**
//     * 添加key value
//     *
//     * @param key
//     * @param value
//     */
//    public abstract void set(K key, String value);
//
//    /**
//     * 添加key value (字节)(序列化)
//     *
//     * @param key
//     * @param value
//     */
//    public abstract void set(byte[] key, byte[] value);
//
//    /**
//     * 获取redis value (String)
//     * @param key
//     * @return
//     */
//    Object get(K key);
//
//    /**
//     * 通过正则匹配keys
//     *
//     * @param pattern
//     * @return
//     */
//    public abstract Setkeys(String pattern);
//
//    /**
//     * 检查key是否已经存在
//     *
//     * @param key
//     * @return
//     */
//    public abstract boolean exists(K key);
//
//    /**
//     * 清空redis 所有数据
//     *
//     * @return
//     */
//    public abstract String flushDB();
//
//    /**
//     * 查看redis里有多少数据
//     */
//    public abstract long dbSize();

}